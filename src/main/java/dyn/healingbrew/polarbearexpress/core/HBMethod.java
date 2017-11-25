package dyn.healingbrew.polarbearexpress.core;

import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider;
import net.minecraft.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@SuppressWarnings("unused")
public class HBMethod {
    public static final Logger logger = LogManager.getLogger("healingbrew.asm");

    public static Entity getControllingPassenger(Entity me) {
        logger.info("Hello");
        ITamableEntity tamableEntity = me.getCapability(TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY, null);
        if(tamableEntity != null && tamableEntity.getUUID() != null) {
            List<Entity> passengers = me.getPassengers();
            if(passengers.size() > 0 && passengers.get(0).getPersistentID() == tamableEntity.getUUID()) {
                return passengers.get(0);
            }
        }
        return null;
    }
}
