package dyn.healingbrew.polarbearexpress.core;

import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider;
import net.minecraft.entity.Entity;

import java.util.List;

@SuppressWarnings("unused")
public class HBMethod {
    public static Entity getControllingPassenger(Entity me) {
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
