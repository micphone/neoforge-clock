package cn.ac.micphone.clock;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

/**
 * Clock —— 客户端 HUD 实时时钟模组。
 *
 * <p>时钟的绘制逻辑在 {@link ClockHudLayer}（仅客户端），这里只负责模组入口。
 */
@Mod(Clock.MODID)
public class Clock {
    public static final String MODID = "clock";

    private static final Logger LOGGER = LogUtils.getLogger();

    public Clock(IEventBus modEventBus) {
        LOGGER.info("[{}] initialized: real-time clock HUD in the top-right corner", MODID);
    }
}
