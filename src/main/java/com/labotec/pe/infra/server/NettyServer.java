package com.labotec.pe.infra.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;
import com.labotec.pe.infra.config.AppConfigConstant;
import com.labotec.pe.infra.factory.TCPServerHandlerFactory;
import com.labotec.pe.infra.metrics.TCPMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NettyServer implements CommandLineRunner {
    private final AppConfigConstant appConfigConstant;
    private final TCPMetrics tcpMetrics;
    private final TCPServerHandlerFactory tcpServerHandlerFactory;
    private final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    public void startServer() throws InterruptedException {
        // Configurar el número de hilos
        int bossGroupThreads = 1;
        int workerGroupThreads = 16;

        // Usar EpollEventLoopGroup para Linux, de lo contrario usar NioEventLoopGroup
        EventLoopGroup bossGroup = isLinux() ? new EpollEventLoopGroup(bossGroupThreads) : new NioEventLoopGroup(bossGroupThreads);
        EventLoopGroup workerGroup = isLinux() ? new EpollEventLoopGroup(workerGroupThreads) : new NioEventLoopGroup(workerGroupThreads);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(isLinux() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new TCPServerHandler(appConfigConstant,tcpMetrics,tcpServerHandlerFactory));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 512)
                    .option(ChannelOption.SO_RCVBUF, 1048576)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true);

            // Iniciar el servidor en el puerto configurado
            ChannelFuture future = serverBootstrap.bind(appConfigConstant.getTcpServerPort()).sync();
            logger.info("Servidor TCP iniciado en el puerto {}", appConfigConstant.getTcpServerPort());
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    @Override
    public void run(String... args) throws Exception {
        startServer();
    }
}