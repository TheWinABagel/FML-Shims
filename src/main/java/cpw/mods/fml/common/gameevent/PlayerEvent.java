package cpw.mods.fml.common.gameevent;

import cpw.mods.fml.common.eventhandler.Event;
import net.legacyfabric.fabric.api.event.EventFactory;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

import java.util.function.Consumer;

public class PlayerEvent extends Event {
    public final EntityPlayer player;
    private PlayerEvent(EntityPlayer player)
    {
        this.player = player;
    }

    public static class ItemPickupEvent extends PlayerEvent {
        public final EntityItem pickedUp;
        public ItemPickupEvent(EntityPlayer player, EntityItem pickedUp)
        {
            super(player);
            this.pickedUp = pickedUp;
        }
    }

    public static class ItemCraftedEvent extends PlayerEvent {
        public final ItemStack crafting;
        public final IInventory craftMatrix;
        public ItemCraftedEvent(EntityPlayer player, ItemStack crafting, IInventory craftMatrix)
        {
            super(player);
            this.crafting = crafting;
            this.craftMatrix = craftMatrix;
        }
        public static final net.legacyfabric.fabric.api.event.Event<Consumer<ItemCraftedEvent>> EVENT = EventFactory.createArrayBacked(Consumer.class, (listeners) -> (player) -> {
            for (Consumer<ItemCraftedEvent> listener : listeners) {
                listener.accept(player);
            }
        });
    }

    public static class PlayerLoggedInEvent extends PlayerEvent {
        public PlayerLoggedInEvent(EntityPlayer player)
        {
            super(player);
        }
    }

    public static class PlayerLoggedOutEvent extends PlayerEvent {
        public static final net.legacyfabric.fabric.api.event.Event<Consumer<PlayerLoggedOutEvent>> EVENT = EventFactory.createArrayBacked(Consumer.class, (listeners) -> (player) -> {
            for (Consumer<PlayerLoggedOutEvent> listener : listeners) {
                listener.accept(player);
            }
        });
        public PlayerLoggedOutEvent(EntityPlayer player)
        {
            super(player);
        }
    }

    public static class PlayerRespawnEvent extends PlayerEvent {
        public PlayerRespawnEvent(EntityPlayer player)
        {
            super(player);
        }
        public static net.legacyfabric.fabric.api.event.Event<PlayerRespawnEventCallback> EVENT = EventFactory.createArrayBacked(PlayerRespawnEventCallback.class, (callbacks) -> (event) -> {
            for (PlayerRespawnEventCallback eventCallback : callbacks) {
                eventCallback.onPlayerRespawn(event);
            }
        });
        public interface PlayerRespawnEventCallback {
            public void onPlayerRespawn(PlayerRespawnEvent event);
        }
    }

    public static class PlayerChangedDimensionEvent extends PlayerEvent {
        public final int fromDim;
        public final int toDim;
        public PlayerChangedDimensionEvent(EntityPlayer player, int fromDim, int toDim)
        {
            super(player);
            this.fromDim = fromDim;
            this.toDim = toDim;
        }
    }
}