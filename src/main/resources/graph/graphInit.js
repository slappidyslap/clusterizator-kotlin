const nodes = new vis.DataSet([]);

const edges = new vis.DataSet([]);

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