package projects.netty.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private final String first_msg = "QUERY TIME ORDER";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client active =============>");
        byte[] order = first_msg.getBytes();
        ByteBuf msg = Unpooled.buffer(order.length);
        msg.writeBytes(order);
        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String body = new String(bytes, StandardCharsets.UTF_8);
        log.info("client receive msg: {}", body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
