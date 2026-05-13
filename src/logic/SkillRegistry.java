package logic;

import java.util.HashMap;

public class SkillRegistry {
    private static HashMap<String, Skill> skillList = new HashMap<>();

    static {
        // Basic Skill: (id, name, manaCost, minDamage, maxDamage, cooldown)
        registerSkill(new Skill("attack", "Attack", 0, 20, 30, 0));

        // Aero's Skills (Wind)
        registerSkill(new Skill("zephyr_splash", "Zephyr Splash", 25, 30, 45, 0));
        registerSkill(new Skill("aether_guard", "Aether Guard", -60, 0, 0, 1));
        registerSkill(new Skill("cyclone_fury", "Cyclone Fury", 100, 120, 160, 2));

        // Psalm's Skills (Fire)
        registerSkill(new Skill("rapid_punch", "Rapid Punch", 20, 35, 55, 0));
        registerSkill(new Skill("healing_fan", "Healing Fan", -40, 0, 0, 1));
        registerSkill(new Skill("fire_kick", "Fire Kick", 90, 130, 180, 2));

        // Kayden's Skills (Lightning)
        registerSkill(new Skill("super_speed", "Super Speed", 15, 15, 50, 0));
        registerSkill(new Skill("force_control", "Force Control", -50, 0, 0, 1));
        registerSkill(new Skill("lightning_strike", "Lightning Strike", 100, 10, 250, 2));

        // Ripper's Skills (Earth)
        registerSkill(new Skill("tackle", "Tackle", 0, 20, 30, 0));
        registerSkill(new Skill("ground_slam", "Ground Slam", 30, 60, 80, 1));
        registerSkill(new Skill("hardening_punch", "Hardening Punch", 50, 90, 130, 3));

        // ZenStream's Skills (Water)
        registerSkill(new Skill("sling_water", "Sling Water", 5, 25, 35, 0));
        registerSkill(new Skill("liquify", "Liquify", 40, 50, 70, 1));
        registerSkill(new Skill("water_takeover", "Water Takeover", 75, 90, 120, 2));

        // Kangel's Skills (Blood)
        registerSkill(new Skill("blood_bind", "Blood Bind", 0, 15, 25, 0));
        registerSkill(new Skill("crimson_spear", "Crimson Spear", 40, 50, 70, 1));
        registerSkill(new Skill("life_drain", "Life Drain", 70, 80, 110, 2));

        // Maelor's Skills (Magnetism)
        registerSkill(new Skill("magnetic_pull", "Magnetic Pull", 5, 20, 35, 0));
        registerSkill(new Skill("repulse_field", "Repulse Field", 45, 60, 85, 1));
        registerSkill(new Skill("iron_storm", "Iron Storm", 80, 100, 140, 2));

        // Kaelis's Skills (Beast)
        registerSkill(new Skill("wolf_call", "Wolf Call", 0, 25, 35, 0));
        registerSkill(new Skill("hawk_sight", "Hawk Sight", 35, 50, 65, 1));
        registerSkill(new Skill("beast_surge", "Beast Surge", 65, 85, 115, 2));

        // Veyrion's Skills (Shadow)
        registerSkill(new Skill("shadow_step", "Shadow Step", 10, 20, 45, 0));
        registerSkill(new Skill("dark_grasp", "Dark Grasp", 50, 65, 95, 1));
        registerSkill(new Skill("night_veil", "Night Veil", 80, 110, 160, 2));
    }

    private static void registerSkill(Skill skill) {
        skillList.put(skill.getId(), skill);
    }

    public static Skill getSkill(String id){
        return skillList.get(id);
    }
}
