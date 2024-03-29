package abilities;

import common.Constants;
import heroes.Knight;
import heroes.Pyromancer;
import heroes.Rogue;
import heroes.Wizard;

public class Drain extends Abilities implements Visitor {
    private float drainRogue;
    private float drainKnight;
    private float drainPyromancer;
    private float drainWizard;
    public Drain() {
        damageprocent = Constants.PERCENT_DRAIN;
        drainRogue = Constants.ROGUE_MODIFICATOR_DR;
        drainKnight = Constants.KNIGHT_MODIFICATOR_DR;
        drainPyromancer = Constants.PYROMANCER_MODIFICATOR_DR;
        drainWizard = Constants.WIZARD_MODIFICATOR_DR;
    }

    /**
     * update the base damage of Drain ability as the hero's level increases.
     */
    public void setDamage() {
        damageprocent += Constants.EXTRA_PERCENT_DRAIN;
    }

    /**
     * set the race modifiers if the player chooses the offensive strategy.
     * @param percent - a percentage that will increase race coefficients.
     */
    public void setCoefOffensive(final float percent) {
        // the race modifiers change if initially they are different from 1
        if (drainRogue != 1) {
            drainRogue += percent;
        }
        if (drainKnight != 1) {
            drainKnight += percent;
        }
        if (drainPyromancer != 1) {
            drainPyromancer += percent;
        }
        if (drainWizard != 1) {
            drainWizard += percent;
        }
    }

    /**
     * set the race modifiers if the player chooses the defensive strategy.
     * @param percent - a percentage that will reduce race coefficients.
     */
    public void setCoefDefensive(final float percent) {
        // the race modifiers change if initially they are different from 1
        if (drainRogue != 1) {
            drainRogue -= percent;
        }
        if (drainKnight != 1) {
            drainKnight -= percent;
        }
        if (drainPyromancer != 1) {
            drainPyromancer -= percent;
        }
        if (drainWizard != 1) {
            drainWizard -= percent;
        }
    }

    /**
     * applying the Drain ability to the Pyromancer hero type.
     * @param p - a Pyromancer-type hero.
     */
    public void visit(final Pyromancer p) {
        float dmgpercent = damageprocent;
        float landBonus = landModificator;
        // applying the lang type bonus
        if (map.Mapworld.getInstance().getlocation(p.getRow(), p.getCol())
                == Constants.DESERT_TYPE) {
            landBonus += Constants.DESERT_BONUS;
        }
        dmgpercent = dmgpercent * landBonus;
        // applying the race modifier
        float damagelandrace = dmgpercent * drainPyromancer;
        float dmg = damagelandrace * Math.min(Constants.HP_MIN_DRAIN * p.getHpMax(),
                p.getHpCurrent());
        int result = Math.round(dmg);
        // decrease of the final damage from the opponent's hp
        p.setHpCurrent(result);
    }

    /**
     * applying the Drain ability to the Knight hero type.
     * @param k - a Knight-type hero.
     */
    public void visit(final Knight k) {
        float dmgpercent = damageprocent;
        float landBonus = landModificator;
        // applying the lang type bonus
        if (map.Mapworld.getInstance().getlocation(k.getRow(), k.getCol())
                == Constants.DESERT_TYPE) {
            landBonus += Constants.DESERT_BONUS;
        }
        dmgpercent = dmgpercent * landBonus;
        // applying the race modifier
        float damagelandrace = dmgpercent * drainKnight;
        float dmg = damagelandrace * Math.min(Constants.HP_MIN_DRAIN * k.getHpMax(),
                k.getHpCurrent());
        int result = Math.round(dmg);
        // decrease of the final damage from the opponent's hp
        k.setHpCurrent(result);
    }

    /**
     * applying the Drain ability to the Rogue hero type.
     * @param r - a Rogue-type hero.
     */
    public void visit(final Rogue r) {
        float dmgpercent = damageprocent;
        float landBonus = landModificator;
        // applying the lang type bonus
        if (map.Mapworld.getInstance().getlocation(r.getRow(), r.getCol())
                == Constants.DESERT_TYPE) {
            landBonus += Constants.DESERT_BONUS;
        }
        dmgpercent = dmgpercent * landBonus;
        // applying the race modifier
        float damagelandrace = dmgpercent * drainRogue;
        float dmg = damagelandrace * Math.min(Constants.HP_MIN_DRAIN * r.getHpMax(),
                r.getHpCurrent());
        int result = Math.round(dmg);
        // decrease of the final damage from the opponent's hp
        r.setHpCurrent(result);
    }

    /**
     * applying the Drain ability to the Wizard hero type.
     * @param w - a Wizard-type hero.
     */
    public void visit(final Wizard w) {
        float dmgpercent = damageprocent;
        float landBonus = landModificator;
        // applying the lang type bonus
        if (map.Mapworld.getInstance().getlocation(w.getRow(), w.getCol())
                == Constants.DESERT_TYPE) {
            landBonus += Constants.DESERT_BONUS;
        }
        dmgpercent = dmgpercent * landBonus;
        float dmg = dmgpercent * Math.min(Constants.HP_MIN_DRAIN * w.getHpMax(), w.getHpCurrent());
        int dmgland = Math.round(dmg);
        // setting the damage received without the race modifier for the wizard hero
        w.setDamageRec(dmgland);
        // applying the race modifier
        float damagelandrace = drainWizard * dmgpercent;
        dmg = damagelandrace * Math.min(Constants.HP_MIN_DRAIN * w.getHpMax(), w.getHpCurrent());
        int result = Math.round(dmg);
        // decrease of the final damage from the opponent's hp
        w.setHpCurrent(result);
    }
}
