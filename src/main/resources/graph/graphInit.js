// create an array with nodes
const nodes = new vis.DataSet([
  { id: 1, label: "Node 1" },
  { id: 2, label: "Node 2" },
]);

// create an array with edges
const edges = new vis.DataSet([
  { from: 1, to: 2 },
]);

// create a network
const container = document.getElementById("graph");
const data = {nodes, edges};
const options = {
  edges: {
    smooth: {
      forceDirection: "none"
    }
  },
  physics: {
    forceAtlas2Based: {
      springLength: 100
    },
    minVelocity: 0.75,
    solver: "forceAtlas2Based"
  }
}
const network = new vis.Network(container, data, options);

window.graph = network