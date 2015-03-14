package tetris;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class GeneticTuning {

	Random random = new Random();

	int nFeatures = 5;
	double fuzzy = 0.2;

	public Player[] spawnPopulation(int popSize, double range) {
		Player[] pop = new Player[popSize];
		for (int i = 0; i < popSize; i++) {
			pop[i] = createPlayer(range);
		}
		return pop;
	}

	private Player createPlayer(double range) {
		double[] weights = new double[nFeatures];
		for (int i = 0; i < nFeatures; i++) {
			weights[i] = randFloat(range);
		}
		return new Player(weights, 0);
	}

	private Player[] runOneGen(Player[] pop) {
		TetrisEngine tetrisEngine = new TetrisEngine();
		for (int i = 0; i < pop.length; i++) {
			pop[i].fitnessFunction = tetrisEngine.startGame(pop[i].weights);
		}
		Arrays.sort(pop, new Comparator<Player>() {
			@Override
			public int compare(Player o1, Player o2) {
				return ((Double) o2.fitnessFunction)
						.compareTo((Double) o1.fitnessFunction);
			}
		});
		return pop;
	}

	private Player[] respawn(Player[] pop) {
		Player[] newGen = new Player[pop.length];
		double sum = 0;
		for (Player p : pop)
			sum += p.fitnessFunction;
		int nchildren = 0;
		for (int i = 0; i < pop.length - 1; i = i + 2) {
			Player mom = pop[i];
			Player dad = pop[i + 1];
			// find proportion
			int nChild = (int) Math.ceil(pop.length
					* (mom.fitnessFunction + dad.fitnessFunction) / sum);
			for(i = nchildren; i < Math.min(nchildren + nChild, pop.length - 1); i++) {
				newGen[i] = mate(dad, mom);
			}
			if(nChild + nchildren >= pop.length - 1) break;
			nchildren += nChild;
		}
		return newGen;
	}

	private Player mate(Player a, Player b) {
		double[] weights = new double[nFeatures];
		for (int i = 0; i < nFeatures; i++) {
			if (random.nextFloat() > 0.5) {
				// dad wins
				weights[i] = a.weights[i] + randFloat(fuzzy);
			} else {
				weights[i] = b.weights[i] + randFloat(fuzzy);
			}
		}
		return new Player(weights, 0);
	}

	public void printGen(Player[] pop) {
		for(Player p : pop) {
			for(double w : p.weights)
				System.out.print(String.format("%.4f", w) + "\t");
			System.out.println(p.fitnessFunction);
		}
		System.out.println();
	}
	private double randFloat(double range) {
		return random.nextFloat() * (range * 2) - range;
	}
	
	public void nGenerations(int n) {
		Player[] gen1 = spawnPopulation(10, 2);
		for(int i = 0; i < n; i++) {
			runOneGen(gen1);
			printGen(gen1);
			respawn(gen1);
			printGen(gen1);
		}
		
	}
	
	public static void main(String[] args) {
		GeneticTuning gt = new GeneticTuning();
		gt.nGenerations(1000);
	}

	class Player {
		double[] weights;
		double fitnessFunction;

		Player(double[] weights, double fitness) {
			this.weights = weights;
			this.fitnessFunction = fitness;
		}
	}
}
