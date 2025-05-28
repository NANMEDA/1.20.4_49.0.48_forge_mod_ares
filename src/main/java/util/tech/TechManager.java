package util.tech;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.checkerframework.common.returnsreceiver.qual.This;

import menu.reseachtable.TechNode;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TechManager {

    // 1. 定义一个静态的 TechNode 集合
    private static Set<TechNode> techNodes;

    // 2. 静态初始化方法，构造并初始化 Set<TechNode>
    public static void initiateTech() {
        techNodes = new HashSet<>();
        
        TechNode calculus = new TechNode("calculus", null,null).genUI().time(3600)
        		.resources(() -> new ArrayList<>(List.of(new ItemStack(Items.PAPER, 8))));
        TechNode linear_algebra = new TechNode("linear_algebra", null,null).genUI().time(3600)
        		.resources(() -> new ArrayList<>(List.of(new ItemStack(Items.PAPER, 8))));;
        TechNode mathematical_statistics = new TechNode("mathematical_statistics", null,null).genUI().time(3600)
        		.resources(() -> new ArrayList<>(List.of(new ItemStack(Items.PAPER, 8))));;
        TechNode basic_astronomy = new TechNode("basic_astronomy", null,null).time(3600)
        		.resources(() -> new ArrayList<>(List.of(new ItemStack(Items.PAPER, 8),new ItemStack(Items.SPYGLASS))));;
        TechNode herbs = new TechNode("herbs", null,null).time(3600)
        		.resources(() -> new ArrayList<>(List.of(new ItemStack(Items.PAPER, 8),new ItemStack(Items.POTION, 8))));;
        
        TechNode modular_building = new TechNode("modular_building", null,false,1,null).time(3600);
        TechNode classical_control_theory = new TechNode("classical_control_theory", null,false,1,null).time(3600);
        TechNode classical_physics = new TechNode("classical_physics", null,false,1,null).time(3600);
        TechNode classical_chemistry = new TechNode("classical_chemistry", null,false,1,null).time(3600);
        TechNode basic_biology = new TechNode("basic_biology", null,false,1,null).time(3600);
        classical_control_theory.addPrerequisite(calculus);
        classical_physics.addPrerequisite(calculus);
        classical_chemistry.addPrerequisite(mathematical_statistics);
        basic_biology.addPrerequisite(herbs);
        basic_biology.addPrerequisite(basic_astronomy);
        basic_biology.addPrerequisite(mathematical_statistics);
        modular_building.addPrerequisite(mathematical_statistics);
        
        TechNode modern_control_theory = new TechNode("modern_control_theory", null,false,2,null).time(3600);
        TechNode astronomy = new TechNode("astronomy", null,false,2,null).time(3600);
        TechNode steam_engine = new TechNode("steam_engine", null,false,2,null).time(3600);
        TechNode biology = new TechNode("biology", null,false,2,null).time(3600);
        TechNode dome = new TechNode("dome", null,false,2,null).time(3600);
        astronomy.addPrerequisite(basic_astronomy);
        astronomy.addPrerequisite(classical_physics);
        biology.addPrerequisite(classical_chemistry);
        biology.addPrerequisite(basic_biology);
        steam_engine.addPrerequisite(classical_control_theory);
        modern_control_theory.addPrerequisite(linear_algebra);
        modern_control_theory.addPrerequisite(classical_control_theory);
        dome.addPrerequisite(modular_building);
        dome.addPrerequisite(classical_control_theory);
        dome.addPrerequisite(classical_chemistry);
        
        TechNode theory_of_relativity = new TechNode("theory_of_relativity", null,false,3,null).time(3600);
        TechNode the_quantum_theory = new TechNode("the_quantum_theory", null,false,3,null).time(3600);
        TechNode electricity = new TechNode("electricity", null,false,3,null).time(3600);
        TechNode diesels = new TechNode("diesels", null,false,3,null).time(3600);
        TechNode environmental_theory = new TechNode("environmental_theory", null,false,3,null).time(3600);
        theory_of_relativity.addPrerequisite(classical_physics);
        theory_of_relativity.addPrerequisite(astronomy);
        electricity.addPrerequisite(steam_engine);
        electricity.addPrerequisite(modern_control_theory);
        the_quantum_theory.addPrerequisite(classical_physics);
        diesels.addPrerequisite(steam_engine);
        environmental_theory.addPrerequisite(biology);
        
        TechNode modern_astronomy = new TechNode("modern_astronomy", null,false,4,null).time(3600);
        TechNode environment_detection = new TechNode("environment_detection", null,false,4,null).time(3600);
        TechNode nuclear_reaction = new TechNode("nuclear_reaction", null,false,4,null).time(3600);
        TechNode laser = new TechNode("laser", null,false,4,null).time(3600);
        TechNode electric_motor = new TechNode("electric_motor", null,false,4,null).time(3600);
        TechNode silicon = new TechNode("silicon", null,false,4,null).time(3600);
        TechNode aerospace = new TechNode("aerospace", null,false,4,null).time(3600);
        TechNode propulsion = new TechNode("propulsion", null,false,4,null).time(3600);
        propulsion.addPrerequisite(diesels);
        modern_astronomy.addPrerequisite(astronomy);
        modern_astronomy.addPrerequisite(theory_of_relativity);
        modern_astronomy.addPrerequisite(the_quantum_theory);
        aerospace.addPrerequisite(modern_control_theory);
        electric_motor.addPrerequisite(diesels);
        electric_motor.addPrerequisite(electricity);
        environment_detection.addPrerequisite(environmental_theory);
        environment_detection.addPrerequisite(electricity);
        nuclear_reaction.addPrerequisite(theory_of_relativity);
        laser.addPrerequisite(theory_of_relativity);
        laser.addPrerequisite(the_quantum_theory);
        laser.addPrerequisite(electricity);
        silicon.addPrerequisite(electricity);
        
        TechNode rocket = new TechNode("rocket", null,false,5,null).time(3600);
        TechNode basic_robot = new TechNode("basic_robot", null,false,5,null).time(3600);
        TechNode terraforming = new TechNode("terraforming", null,false,5,null).time(3600);
        TechNode M_theory = new TechNode("M_theory", null,false,5,null).time(3600);
        TechNode advance_laser = new TechNode("advance_laser", null,false,5,null).time(3600);
        TechNode quark = new TechNode("quark", null,false,5,null).time(3600);
        TechNode chip = new TechNode("chip", null,false,5,null).time(3600);
        chip.addPrerequisite(silicon);
        chip.addPrerequisite(laser);
        rocket.addPrerequisite(modern_astronomy);
        rocket.addPrerequisite(aerospace);
        rocket.addPrerequisite(propulsion);
        basic_robot.addPrerequisite(electric_motor);
        terraforming.addPrerequisite(environment_detection);
        terraforming.addPrerequisite(modern_control_theory);
        M_theory.addPrerequisite(theory_of_relativity);
        M_theory.addPrerequisite(the_quantum_theory);
        M_theory.addPrerequisite(nuclear_reaction);
        quark.addPrerequisite(the_quantum_theory);
        advance_laser.addPrerequisite(laser);
        
        /**
         * 5级都是基础科技
         * 5级之后就不要用before的了
         * */
        
        TechNode stellar_station = new TechNode("stellar_station", null,false,6,null).time(3600);
        TechNode antimatter = new TechNode("antimatter", null,false,6,null).time(3600);
        TechNode asteroid_rope = new TechNode("asteroid_rope", null,false,6,null).time(3600);
        TechNode robot = new TechNode("robot", null,false,6,null).time(3600);
        TechNode gravitational_wave = new TechNode("gravitational_wave", null,false,6,null).time(3600);
        stellar_station.addPrerequisite(rocket);
        antimatter.addPrerequisite(quark);
        antimatter.addPrerequisite(advance_laser);
        asteroid_rope.addPrerequisite(rocket);
        robot.addPrerequisite(basic_robot);
        robot.addPrerequisite(chip);
        gravitational_wave.addPrerequisite(M_theory);
        
        techNodes.add(propulsion);
        
        techNodes.add(gravitational_wave);
        techNodes.add(silicon);
        techNodes.add(chip);
        techNodes.add(aerospace);
        techNodes.add(stellar_station);
        
        techNodes.add(advance_laser);
        techNodes.add(quark);
        techNodes.add(antimatter);
        techNodes.add(asteroid_rope);
        techNodes.add(robot);
        
        techNodes.add(calculus);	//
        techNodes.add(linear_algebra);	//
        techNodes.add(mathematical_statistics); //
        techNodes.add(classical_chemistry); //
        techNodes.add(herbs); //
        
        techNodes.add(basic_biology); //
        techNodes.add(basic_astronomy);	//
        techNodes.add(astronomy);  //
        techNodes.add(classical_physics);  //
        techNodes.add(steam_engine);  //
        
        techNodes.add(classical_control_theory);  //
        techNodes.add(modern_control_theory);  //
        techNodes.add(theory_of_relativity);  //
        techNodes.add(the_quantum_theory);  //
        techNodes.add(modern_astronomy);  //
        
        techNodes.add(diesels);  //
        techNodes.add(rocket);
        techNodes.add(biology);	//
        techNodes.add(modular_building);
        techNodes.add(dome);

        techNodes.add(nuclear_reaction); //
        techNodes.add(environmental_theory); 
        techNodes.add(environment_detection);
        techNodes.add(terraforming); 
        techNodes.add(M_theory); //
       
        techNodes.add(electric_motor);  //
        techNodes.add(basic_robot);  //
        techNodes.add(laser);  //
        techNodes.add(electricity);  //
    }

    // 3. 获取静态 Set<TechNode> 的方法
    /**
     * 不管怎样
     * TechNode永远是一个
     * */
    public static Set<TechNode> getTechNodes() {
        return new HashSet<>(techNodes); // 返回集合的副本，避免外部直接修改
    }
    public static Set<TechNode> getOriginalTechNodes() {
        return techNodes;
    }

    // 4. 静态重构方法，用于重新初始化 Set<TechNode>
    public static void resetTechNodes(Set<TechNode> newTechNodes) {
        techNodes = new HashSet<>(newTechNodes);
    }
    
    public static TechNode findTechNodeByName(String name) {
        
        // 遍历集合查找匹配的 TechNode
        for (TechNode node : techNodes) {
            if (node.getName().equals(name)) {
                return node;  // 找到匹配的 TechNode，返回
            }
        }
        
        // 如果没有找到匹配的 TechNode，返回 null 或者抛出异常
        return null; // 可以根据需要改成抛出异常
    }
    
    public static void register(TechNode node) {
    	TechManager.techNodes.add(node);
    }


}