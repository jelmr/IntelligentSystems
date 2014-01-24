import java.util.ArrayList;


/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 22/01/14
 */
public class NeuralNetwork {

	Node[][] layers;
	Node[] root;


	public NeuralNetwork() {
		double[] pars = new double[7];

		for (int i = 0; i < pars.length; i++) {
			pars[i] = Math.random();
		}

		root = builtNetwork(pars);
	}


	public void setWeights(double[] pars) {

		int counter = 0;

		for (Node[] layer : layers) {
			for (Node node : layer) {
				if (node instanceof NeuralNode) {
					double[] weights = ((NeuralNode) node).weights;
					for (int i = 0; i < weights.length; i++) {
						weights[i] = pars[counter++];
					}
				}
			}
		}
	}


	private Node[] builtNetwork(double[] inputValues) {
		// Created input layer

		Node[] inputLayer = new Node[inputValues.length];

		for (int i = 0; i < inputValues.length; i++) {
			inputLayer[i] = new InputNode(inputValues[i]);
		}

		// Create hidden layer
		Node[] hiddenLayer = new NeuralNode[5];

		for (int i = 0; i < hiddenLayer.length; i++) {
			// Generate random weights
			double[] weights = new double[inputValues.length];
			for (int j = 0; j < inputValues.length; j++) {
//				weights[j] = Math.random();
				weights[j] = 1;
			}

			hiddenLayer[i] = new NeuralNode(inputLayer, weights);
		}

		//Create output layer

		Node[] outputLayer = new NeuralNode[4];

		for (int i = 0; i < outputLayer.length; i++) {
			// Generate random weights
			double[] weights = new double[hiddenLayer.length];
			for (int j = 0; j < hiddenLayer.length; j++) {
//				weights[j] = Math.random();
				weights[j] = 1;
			}

			outputLayer[i] = new NeuralNode(hiddenLayer, weights);
		}


		layers = new Node[][]{inputLayer, hiddenLayer, outputLayer};

		return outputLayer;

	}


	public NeuralNetwork(double... pars) {
		root = builtNetwork(pars);
	}


	public static void main(String[] args) {
		new NeuralNetwork();
	}


	public double[] getValues() {
		double[] result = new double[root.length];

		for (int i = 0; i < result.length; i++) {
			result[i] = root[i].getValue();
		}

		return result;
	}


	public void setValues(double[] values) {
		for (int i = 0; i < layers[0].length; i++) {
			((InputNode) layers[0][i]).value = values[i];
		}
		clearCache();
	}


	private void clearCache() {
		for (Node node : root) {
			node.clearCache();
		}
	}


	private interface Node {

		void clearCache();
		double getValue();
	}

	private class NeuralNode implements Node {

		double[] weights;
		Node[] children;
		double cachedValue;
		boolean cached;


		private NeuralNode(Node[] children, double[] weights) {
			this.weights = weights;
			this.children = children;
			cached = false;
			cachedValue = -Double.MAX_VALUE;
		}




		@Override
		public void clearCache() {
			cached = false;

			for (Node child : children) {
				child.clearCache();
			}

		}



		@Override
		public double getValue() {
			if (cached) {
				return cachedValue;
			}

			double result = 0;

			for (int i = 0; i < children.length; i++) {
				result += children[i].getValue() * weights[i];

			}

			cached = true;
			cachedValue = result;

			return result;
		}
	}

	private class InputNode implements Node {

		double value;


		private InputNode(double value) {
			this.value = value;
		}


		@Override
		public void clearCache() {
		}



		@Override
		public double getValue() {
			return value;
		}
	}
}
