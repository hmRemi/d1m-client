package com.d1m.client.management.utils.combat;

import com.d1m.client.D1m;
import net.minecraft.client.*;
import com.google.common.base.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class RaycastUtil
{
    private static final Minecraft MC;

    public final Entity raycastEntity(double range, final float[] rotations) {
        final Entity player = RaycastUtil.MC.getRenderViewEntity();
        if (player != null && RaycastUtil.MC.theWorld != null) {
            final Vec3 eyeHeight = player.getPositionEyes(RaycastUtil.MC.timer.renderPartialTicks);
            final Vec3 looks = D1m.ROTATION_UTIL.getVectorForRotation(rotations[0], rotations[1]);
            final Vec3 vec = eyeHeight.addVector(looks.xCoord * range, looks.yCoord * range, looks.zCoord * range);
            final List<Entity> list = RaycastUtil.MC.theWorld.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().addCoord(looks.xCoord * range, looks.yCoord * range, looks.zCoord * range).expand(1.0, 1.0, 1.0), (Predicate<? super Entity>) Predicates.and((Predicate) EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
            Entity raycastedEntity = null;
            for (final Entity entity : list) {
                if (!(entity instanceof EntityLivingBase)) {
                    continue;
                }
                final float borderSize = entity.getCollisionBorderSize();
                final AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
                final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(eyeHeight, vec);
                if (axisalignedbb.isVecInside(eyeHeight)) {
                    if (range < 0.0) {
                        continue;
                    }
                    raycastedEntity = entity;
                    range = 0.0;
                }
                else {
                    if (movingobjectposition == null) {
                        continue;
                    }
                    final double distance = eyeHeight.distanceTo(movingobjectposition.hitVec);
                    if (distance >= range && range != 0.0) {
                        continue;
                    }
                    if (entity == player.ridingEntity) {
                        if (range != 0.0) {
                            continue;
                        }
                        raycastedEntity = entity;
                    }
                    else {
                        raycastedEntity = entity;
                        range = distance;
                    }
                }
            }
            return raycastedEntity;
        }
        return null;
    }

    public final Entity surroundEntity(final Entity target) {
        Entity entity = target;
        for (final Entity possibleTarget : RaycastUtil.MC.theWorld.loadedEntityList) {
            if (possibleTarget.isInvisible()) {
                if (target.getDistanceToEntity(possibleTarget) > 0.5) {
                    continue;
                }
                if (RaycastUtil.MC.thePlayer.getDistanceToEntity(possibleTarget) >= RaycastUtil.MC.thePlayer.getDistanceToEntity(entity)) {
                    continue;
                }
                entity = possibleTarget;
            }
        }
        return target;
    }

    public final BlockPos raycastPosition(final double range) {
        final Entity renderViewEntity = RaycastUtil.MC.getRenderViewEntity();
        if (renderViewEntity == null || RaycastUtil.MC.theWorld == null) {
            return null;
        }
        final MovingObjectPosition movingObjectPosition = renderViewEntity.rayTrace(range, 1.0f);
        if (RaycastUtil.MC.theWorld.getBlockState(movingObjectPosition.getBlockPos()).getBlock() instanceof BlockAir) {
            return null;
        }
        return movingObjectPosition.getBlockPos();
    }

    static {
        MC = Minecraft.getMinecraft();
    }
}

