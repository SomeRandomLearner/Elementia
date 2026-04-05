package logic;

import java.util.HashMap;

public class SkillRegistry {
    public static HashMap<String, Skill> skillList = new HashMap<>();

    static {
        // Basic Skill: (id, name, manaCost, minDamage, maxDamage, cooldown)
        registerSkill(new Skill("attack", "Attack", 0, 20, 30, 0));

        // ZenStream's Skills
        registerSkill(new Skill("sling_water", "Sling Water", 0, 20, 40, 0));
        registerSkill(new Skill("liquify", "Liquify", 25, 20, 80, 1));
        registerSkill(new Skill("water_takeover", "Water Takeover", 60, 80, 120, 2));

        // Kayden's Skills
        registerSkill(new Skill("super_speed", "Super Speed", 25, 50, 75, 0));
        registerSkill(new Skill("force_control", "Force Control", -40, 0, 0, 1)); // restores mana only
        registerSkill(new Skill("lightning_strike", "Lightning Strike", 100, 60, 150, 5));

        // Aero's Skills
        registerSkill(new Skill("zephyr_splash", "Zephyr Splash", 15, 15, 45, 0));
        registerSkill(new Skill("cyclone_fury", "Cyclone Fury", 80, 70, 119, 2));
        registerSkill(new Skill("aether_guard", "Aether Guard", -60, 0, 0, 3)); // restores mana only;

        // Psalm's Skills (Fire)
        registerSkill(new Skill("rapid_punch", "Rapid Punch", 10, 45, 65, 0));
        registerSkill(new Skill("healing_fan", "Healing Fan", -40, 0, 0, 1)); // restores mana only
        registerSkill(new Skill("fire_kick", "Fire Kick", 100, 80, 120, 4));

        // Ripper's Skills (Earth)
        registerSkill(new Skill("tackle", "Tackle", 0, 30, 35, 0));
        registerSkill(new Skill("ground_slam", "Ground Slam", 35, 70, 80, 3));
        registerSkill(new Skill("hardening_punch", "Hardening Punch", 80, 100, 110, 3));

        // Kangel's Skills
        registerSkill(new Skill("blood_bind", "Blood Bind", 20, 15, 25, 0));
        registerSkill(new Skill("crimson_spear", "Crimson Spear", 30, 30, 70, 2));
        registerSkill(new Skill("life_drain", "Life Drain", 70, 100, 150, 5));

        // Maelor's Skills
        registerSkill(new Skill("magnetic_pull", "Magnetic Pull", 0, 20, 30, 0));
        registerSkill(new Skill("repulse_field", "Repulse Field", 10, 60, 80, 2));
        registerSkill(new Skill("iron_storm", "Iron Storm", 55, 10, 200, 3));

        // Kaelis' Skills
        registerSkill(new Skill("wolf_call", "Wolf Call", 20, 20, 30, 0));
        registerSkill(new Skill("hawk_sight", "Hawk Sight", 15, 10, 50, 2));
        registerSkill(new Skill("beast_surge", "Beast Surge", 70, 80, 120, 4));

        // Veyrion's Skills (Shadow)
        registerSkill(new Skill("shadow_step", "Shadow Step", 15, 10, 50, 0));
        registerSkill(new Skill("dark_grasp", "Dark Grasp", 30, 25, 70, 2));
        registerSkill(new Skill("night_veil", "Night Veil", 55, 35, 110, 5));
    }

    private static void registerSkill(Skill skill) {
        skillList.put(skill.getId(), skill);
    }

    public static Skill getSkill(String id){
        return skillList.get(id);
    }
}
