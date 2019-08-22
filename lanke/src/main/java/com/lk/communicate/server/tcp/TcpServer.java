package com.lk.communicate.server.tcp;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class TcpServer {

    private static final Logger log = LoggerFactory.getLogger(TcpServer.class);

    private int port;

    public TcpServer(int port) {
        this.port = port;
    }

    public void start() {
    	log.info("TcpServer start.");
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sbs = new ServerBootstrap().group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(20, 0, 0, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new LankerDecoder());
                            ch.pipeline().addLast(new LankerEncoder());
                            ch.pipeline().addLast(new ServerHandler2());
                        };

                    }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);




            // 绑定端口，开始接收进来的连接  
            ChannelFuture future = sbs.bind(port).sync();


            log.info("Server start listen at " + port);
            future.channel().closeFuture().sync();

        } catch (Exception e) {
        	e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }



}
