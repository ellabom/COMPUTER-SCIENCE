//EMMANUELLA EYO	11291003	EEE917	CMPT280

import lib280.list.LinkedList280;
import lib280.tree.BasicMAryTree280;

public class SkillTree extends BasicMAryTree280<Skill> {

	/**	
	 * Create lib280.tree with the specified root node and
	 * specified maximum arity of nodes.  
	 * @timing O(1) 
	 * @param x item to set as the root node
	 * @param m number of children allowed for future nodes 
	 */
	public SkillTree(Skill x, int m)
	{
		super(x,m);
	}

	/**
	 * A convenience method that avoids typecasts.
	 * Obtains a subtree of the root.
	 * 
	 * @param i Index of the desired subtree of the root.
	 * @return the i-th subtree of the root.
	 */
	public SkillTree rootSubTree(int i) {
		return (SkillTree)super.rootSubtree(i);
	}

	/**
	 *Return a list of dependent skills including the skill
	 * @param SkillName name of a skill
	 * @return a LinkedList of the dependent skills
	 * @precond: throw a RuntimeException if skillname not found
	 */
	public LinkedList280<Skill> skillDependencies(String SkillName){
		LinkedList280<Skill> dependencies = new LinkedList280<Skill>();

		if(searchTree(SkillName, dependencies, this)){
			return dependencies;
		}
		else{
			throw new RuntimeException(SkillName + "not found");
		}

	}

	/**
	 *
	 * @param skillName a name of skill
	 * @param depend	List of dependent skills
	 * @param treeRoot	tree root to search
	 * @return a boolean value if skill name was found
	 */
	protected boolean searchTree(String skillName, LinkedList280<Skill> depend, SkillTree treeRoot){
		if(treeRoot.isEmpty()){
			return false;
		}
		if(treeRoot.rootItem().getSkillName().compareTo(skillName) == 0){
			depend.insert(treeRoot.rootItem());
			return true;
		}

		for(int i = 1; i <= rootLastNonEmptyChild(); i++){

			if(searchTree(skillName, depend, treeRoot.rootSubTree(i))) {
				depend.insert(treeRoot.rootItem());
				return true;
			}
		}
		return false;
	}

	/**
	 *Obtain the total skill cost
	 * @param skillName name of the skill
	 * @param root root of the m-nary tree
	 * @return the total cost to obtain a skill
	 * @precond: if skillname not found, throw RuntimeException
	 */
	public int skillTotalCost(String skillName, SkillTree root){
		int Total_cost;

		if(root.rootItem().getSkillName().compareTo(skillName)==0){
			Total_cost = 0;
		}else
			Total_cost = root.rootItem().getSkillCost();

		LinkedList280<Skill> skillList = skillDependencies(skillName);

		skillList.goFirst();
		while(skillList.itemExists()){
			Total_cost += skillList.item().getSkillCost();
			skillList.goForth();
		}

		return Total_cost;
	}

	public static void main(String[] args){

		Skill ShieldSlam = new Skill("Shield Slam","Slam opponent, causing physical damage", 2);
		Skill victoryRush = new Skill("Victory Rush", "Strike the opponent", 2);
		Skill UnyieldingM = new Skill("Unyielding Might", "Increase the maximum amount og the StormBreaker by 15%", 0);
		Skill Taunt = new Skill("Taunt", "Forces the target to turn towards you for 10 seconds", 2);
		Skill ExecuteThrow = new Skill("Execute Throw", "Attempt to finish off oponent by throwing weapon", 4);
		Skill victorySlam = new Skill( "Victory Slam", "Strike opponent by slamming with The Stormbreaker", 2);
		Skill KineticSlam = new Skill("Kinetic Slam", "Use the suits energy to a signature attack on the opponent", 4);
		Skill ShieldBlock = new Skill("Shield Block","Block incoming attacks", 0);
		Skill KineticBurst = new Skill("Kinetic Burst", "Perform enhanced movements and attacks", 3);
		Skill CounterStrike = new Skill("Counter Strike",
				"Hold off an enemy attack with a SHIELD BLOCK to perform a powerful shield counter", 3);
		Skill IronGaunt = new Skill("Iron Gauntlet", "Lunge forward and engange enemies in close combact range", 4);
		Skill ThrusterU = new Skill("Thruster Uppercut", "Perform a thruster assisted uppercut", 4);


		SkillTree Tree = new SkillTree(ShieldSlam, 2);
		Tree.setRootSubtree(new SkillTree(ShieldBlock, 3), 1);
		Tree.setRootSubtree(new SkillTree(IronGaunt, 4), 2);

		//node for ShieldBlock
		SkillTree subTree = (SkillTree) Tree.rootSubtree(1);
		SkillTree victoryR = new SkillTree(victoryRush, 2);

		subTree.setRootSubtree(victoryR,  1);
		subTree.setRootSubtree(new SkillTree(Taunt, 0), 2);
		subTree.setRootSubtree(new SkillTree(CounterStrike, 2), 3);

		//Victory Rush
		SkillTree subTree1 = subTree.rootSubTree(1);
		SkillTree KTree = new SkillTree(KineticSlam, 3);
		subTree1.setRootSubtree(KTree, 1);
		subTree1.setRootSubtree(new SkillTree(victorySlam, 0),2);

		//Kinetic Slam
		SkillTree subK = subTree1.rootSubTree(1);
		subK.setRootSubtree(new SkillTree(KineticBurst, 0), 1);

		//Counter Strike
		SkillTree cSubtree = subTree.rootSubTree(3);
		SkillTree yieldTree = new SkillTree(UnyieldingM, 2);
		yieldTree.setRootSubtree(new SkillTree(ExecuteThrow, 0), 1);
		cSubtree.setRootSubtree(yieldTree,1);

		//IronGauntlet
		SkillTree ironTree = Tree.rootSubTree(2);
		ironTree.setRootSubtree(new SkillTree(ThrusterU, 0), 1);



		System.out.println("My Skill Tree: \n" + Tree.toStringByLevel() + "\n");



		System.out.println("Dependencies for Kinetic Burst: ");
		System.out.println(Tree.rootSubTree(1).skillDependencies("Kinetic Burst"));

		System.out.println("Dependencies for Thruster Uppercut: ");
		System.out.println(Tree.rootSubTree(2).skillDependencies("Thruster Uppercut"));

		try {
			System.out.println("Dependencies for Iron: ");
			System.out.println(Tree.rootSubTree(4).skillDependencies("Iron"));
		}catch (RuntimeException e){
			System.out.println("Iron not found");
		}

		try {
			System.out.println("Dependencies for Aerial Thrust");
			System.out.println(Tree.rootSubTree(1).skillDependencies("Aerial Thrust"));
		}catch (RuntimeException e){
			System.out.println("Aerial Thrust not found");
		}


		SkillTree Tree1 = Tree.rootSubTree(1).rootSubTree(3);
		int costExecutionT = Tree1.skillTotalCost("Execute Throw", Tree);
		System.out.println("To get Execution Throw you must invest " + costExecutionT + " points");

		SkillTree Tree2 = Tree.rootSubTree(2);
		int Thrust = Tree2.skillTotalCost("Thruster Uppercut", Tree);
		System.out.println("To get Thruster Uppercut you must invest " + Thrust + " points");

		int shieldSlamCost = Tree.skillTotalCost("Shield Slam", Tree);
		System.out.println("To get Shield Slam you must invest " + shieldSlamCost + " points");

		try {
			int ironCost = Tree.skillTotalCost("Iron", Tree);
			System.out.println("To get ironCost you must invest " + ironCost + " points");
		}catch (RuntimeException e){
			System.out.println("Iron skill not found");
		}
	}

}
