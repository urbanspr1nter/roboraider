/*
 * CombatStage.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * Defines the various states in which the battle can flow to.
 */

package com.mygdx.game.battle;

public enum CombatStage {
    // Displays the following and waits for player input:
    //  Attack, Skill, Item, Run.
    WaitingAction,

    // Sometimes there needs to be a slight delay before having the
    // enemy attack. Use this state to handle that.
    DelayEnemyAttack,

    // The state which defines the enemy's turn
    EnemyAttack,

    // The states which an attack can flow to.
    ActionAttack, // Display list of monsters
    ActionAttackApply, // Apply the melee damage
    ActionAttackApplyShowDamage, // Show the damage done by the melee action
    ActionAttackApplyComplete, // Completed the attacked

    // The states which a skill usage can flow to.
    ActionSkill, // Display list of skills
    ActionSkillTarget, // Skill chosen. Player should determine who to use skill on... player, or monster?
    ActionSkillTargetChosen, // Target has been chosen for the skill.
    ActionSkillApply, // Apply the skill on the target
    ActionSkillApplyShowDamage, // Show the damage of the skill
    ActionSkillApplyComplete, // Done using the skill!

    // The states in which using an item can flow to.
    ActionItem, // Display list of item.
    ActionItemTarget, // Valid item chosen. Player should determine who to use item on... player, or monster?
    ActionItemApply, // Apply the item on the target.
    ActionItemInvalid, // The item cannot be used in battle. Check by item.isCombatable()

    // The states in which choosing run can flow to.
    ActionRun, // Check to see if we can run. Is anyone a boss? Is it a scripted battle?
    ActionRunBoss, // Cannot run due to boss, or scripted battle.
    ActionRunSuccess, // Can run. End the battle.
    ActionRunInvalid, // Cannot run for various reasons
    BattleEndFromRun, // Player has ran away.

    // The states in which how the end of a battle is handled
    BattleEnd, // Player has won the battle. Stop battle music, play victory bgm, etc.
    BattleEndDeath, // Battle has ended from death
    BattleEndApplied, // Display relevant data.
    BattleEndExp, // Get EXP
    BattleEndExpApplied, // EXP has been applied.
    BattleEndLevelUp, // Level up if needed.
    BattleEndLevelUpApplied, // Level Up has been applied.
    BattleEndItem, // Get Item
    BattleEndItemApplied, // Item has been placed into inventory
    BattleEndExit, // Exit now

    // Finishing the battle.
    Done, // Fade out, and cleaned up
    Exited; // Nothing more.
}
