package io.github.wjgs.broxely.common.entity;

import io.github.wjgs.broxely.common.init.BroxelyItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;

public class EntityWoodPlanks extends ItemEntity {

    private static EntityType<ItemEntity> TYPE = EntityType.ITEM;
    private int m_changeDelay = 0;
    private int m_lightningDelay = 0;
    private boolean m_beingConverted = false;
    private LightningBoltEntity m_lightning;

    private boolean m_converted = false;


    public EntityWoodPlanks(World worldIn) {
        super(TYPE, worldIn);
    }

    public EntityWoodPlanks(EntityType<Entity> entityEntityType, World world) {
        super(TYPE, world);
    }

    public EntityWoodPlanks(ItemEntity itemEntity) {
        super(itemEntity.getEntityWorld(), itemEntity.getPosition().getX(), itemEntity.getPosition().getY(), itemEntity.getPosition().getZ(), itemEntity.getItem());
    }

    @Override
    public boolean isInvulnerableTo(@Nonnull DamageSource source) {
        if (m_converted) return true;

        if (source == DamageSource.IN_FIRE || source == DamageSource.ON_FIRE) {
            if (m_changeDelay >= 20)
                startRitual();
            else
                despawnIfDoesNotMeetConditions();

            return true;
        }
        else if (m_beingConverted) return true;

        return super.isInvulnerableTo(source);
    }

    private void despawnIfDoesNotMeetConditions() {
        if (meetsConvertConditions()) {
            m_changeDelay++;
            world.getGameRules().get(GameRules.DO_FIRE_TICK).set(false, getServer());
        } else {
            world.getGameRules().get(GameRules.DO_FIRE_TICK).set(true, getServer());
            remove();
        }
    }

    private void startRitual() {
        if (!m_beingConverted) {
            m_beingConverted = true;
            m_lightning = new LightningBoltEntity(world, getPosition().getX(), getPosition().getY(), getPosition().getZ(), false);
        }

        if (meetsConvertConditions()) {

            if (world instanceof ServerWorld) {
                fireLightnings();
            }
            if (m_lightningDelay == 30) {
                convert();
            }
        }

    }

    private void fireLightnings() {

        if (m_lightningDelay % 15 == 0)
            ((ServerWorld) world).addLightningBolt(m_lightning);

        m_lightningDelay++;
    }

    private void convert() {
        BlockPos pos = getPosition();
        world.removeBlock(getPosition().down(), false);

        world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 5f, Explosion.Mode.DESTROY);
        world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(),5.1f, Explosion.Mode.NONE);

        world.getGameRules().get(GameRules.DO_FIRE_TICK).set(true, getServer());

        setItem(new ItemStack(BroxelyItems.fireInfusedPlanks));
        setGlowing(true);

        m_beingConverted = false;
        m_converted = true;
        setNoGravity(true);
        setPosition(pos.getX(), pos.getY(), pos.getZ());
    }

    private boolean meetsConvertConditions() {
        Block block = world.getBlockState(getPosition()).getBlock();
        Block blockUnder = world.getBlockState(getPosition().add(0, -1, 0)).getBlock();

        return ( block == Blocks.FIRE ) && ( blockUnder == Blocks.DIAMOND_BLOCK &&
                world.isThundering()) && world.canBlockSeeSky(getPosition());


    }

    @Override
    public boolean isBurning() {
        return false;
    }

    @Override
    public void tick() {
        if (m_converted)
            setMotion(0, 0, 0);
        super.tick();
    }
}
