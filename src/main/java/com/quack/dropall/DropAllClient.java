package com.quack.dropall;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

public class DropAllClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenEvents.AFTER_INIT.register((client, screen, width, height) -> {
            if (!(screen instanceof HandledScreen<?> handledScreen)) return;

            int x = handledScreen.x + handledScreen.backgroundWidth - 52;
            int y = handledScreen.y + 4;

            ButtonWidget dropAllButton = ButtonWidget.builder(
                    Text.literal("Drop All"),
                    button -> dropAll(handledScreen)
            ).dimensions(x, y, 50, 20).build();

            screen.addDrawableChild(dropAllButton);
        });
    }

    private void dropAll(HandledScreen<?> screen) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        ScreenHandler handler = screen.getScreenHandler();
        int containerSlots = handler.slots.size() - client.player.getInventory().size();

        for (int i = 0; i < containerSlots; i++) {
            client.interactionManager.clickSlot(
                    handler.syncId,
                    i,
                    1,
                    SlotActionType.THROW,
                    client.player
            );
        }
    }
}
