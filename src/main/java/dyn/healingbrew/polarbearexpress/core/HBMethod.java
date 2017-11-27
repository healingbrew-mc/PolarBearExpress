package dyn.healingbrew.polarbearexpress.core;

import dyn.healingbrew.polarbearexpress.common.capability.generic.ITamableEntity;
import dyn.healingbrew.polarbearexpress.common.capability.provider.TamableEntityProvider;
import dyn.healingbrew.polarbearexpress.dummy.DummyPolarBear;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@SuppressWarnings("unused")
public class HBMethod {
    public static final Logger logger = LogManager.getLogger("healingbrew.asm");

    public static Entity getControllingPassenger(Entity me) {
        ITamableEntity tamableEntity = me.getCapability(TamableEntityProvider.TAMABLE_ENTITY_CAPABILITY, null);
        if(tamableEntity != null && tamableEntity.getUUID() != null) {
            List<Entity> passengers = me.getPassengers();
            if(passengers.size() > 0) {
                return passengers.get(0);
            }
        }
        return null;
    }

    public static float[] travel(Entity entity, float strafe, float vertical, float forward) {
        EntityLivingBase rider = (EntityLivingBase)getControllingPassenger(entity);
        if(rider != null) {
            entity.rotationYaw = rider.rotationYaw;
            entity.prevRotationYaw = entity.rotationYaw;
            entity.rotationPitch = rider.rotationPitch * 0.5F;
            entity.rotationYaw = entity.rotationYaw % 360.0F;
            entity.rotationPitch = entity.rotationPitch % 360.0F;

            strafe = rider.moveStrafing * 0.5F;
            forward = rider.moveForward;

            EntityLivingBase living = (EntityLivingBase) entity;

            living.setAIMoveSpeed((float)living.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        }
        return new float[] {strafe, vertical, forward};
    }
}
