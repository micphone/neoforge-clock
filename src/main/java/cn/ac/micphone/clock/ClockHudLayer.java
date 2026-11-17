package cn.ac.micphone.clock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.GuiLayer;

/**
 * 在游戏画面右上角绘制本机实时时间，格式 {@code yyyy-MM-dd HH:mm}。
 *
 * <p>该层由 {@link RegisterGuiLayersEvent} 注册到 HUD 最上层，每帧读取一次系统时间，
 * 因此分钟变化会立即反映到画面上。
 */
@EventBusSubscriber(modid = Clock.MODID, value = Dist.CLIENT)
public final class ClockHudLayer implements GuiLayer {
    /** 显示格式：yyyy-MM-dd HH:mm，例如 2026-11-17 08:34。 */
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /** 本模组 HUD 层的唯一 id：clock:clock。 */
    private static final Identifier LAYER_ID = Identifier.fromNamespaceAndPath(Clock.MODID, "clock");

    /** 距屏幕右上角的边距（GUI 缩放坐标）。 */
    private static final int MARGIN = 4;
    /** 半透明黑底，保证在天空等明亮背景上也能看清。 */
    private static final int BACKGROUND_COLOR = 0x80000000;
    /** 白色文字。 */
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    /** 底框相对文字的高度（原版字体行高为 9）。 */
    private static final int LINE_HEIGHT = 9;

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(LAYER_ID, new ClockHudLayer());
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        if (font == null) {
            return;
        }

        String time = TIME_FORMAT.format(LocalDateTime.now());
        int width = font.width(time);
        int x = graphics.guiWidth() - width - MARGIN - 2;
        int y = MARGIN;

        graphics.fill(x - 2, y - 1, x + width + 2, y + LINE_HEIGHT + 1, BACKGROUND_COLOR);
        graphics.text(font, time, x, y, TEXT_COLOR);
    }
}
