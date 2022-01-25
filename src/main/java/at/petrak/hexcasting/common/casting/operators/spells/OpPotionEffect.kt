package at.petrak.hexcasting.common.casting.operators.spells

import at.petrak.hexcasting.api.Operator.Companion.getChecked
import at.petrak.hexcasting.api.RenderedSpell
import at.petrak.hexcasting.api.SpellDatum
import at.petrak.hexcasting.api.SpellOperator
import at.petrak.hexcasting.common.casting.CastingContext
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import kotlin.math.max
import kotlin.math.pow

class OpPotionEffect(val effect: MobEffect, val baseCost: Int, val potency: Boolean) : SpellOperator {
    override val argc: Int
        get() = if (this.potency) 3 else 2

    override fun execute(args: List<SpellDatum<*>>, ctx: CastingContext): Pair<RenderedSpell, Int> {
        val target = args.getChecked<LivingEntity>(0)
        val duration = max(args.getChecked(1), 0.0)
        val potency = if (this.potency)
            max(args.getChecked(2), 1.0)
        else 1.0

        val cost = (this.baseCost * duration).pow(potency)
        return Pair(
            Spell(effect, target, duration, potency),
            cost.toInt()
        )
    }

    private class Spell(val effect: MobEffect, val target: LivingEntity, val duration: Double, val potency: Double) :
        RenderedSpell {
        override fun cast(ctx: CastingContext) {
            val effectInst = MobEffectInstance(effect, (duration * 20).toInt(), potency.toInt())
            target.addEffect(effectInst)
        }
    }
}