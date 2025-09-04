const nodes = new vis.DataSet([]);

const edges = new vis.DataSet([]);

const container = document.getElementById("graph");
const data = {nodes, edges};
const options = {
  edges: {
    smooth: {
      forceDirection: "none"
    },
    arrows: {
      to: { enabled: true }
    }
  },
  physics: {
    forceAtlas2Based: {
      springLength: 100
    },
    minVelocity: 0.75,
    solver: "forceAtlas2Based"
  },
//  layout: {
////    hierarchical: {
////      sortMethod: 'directed', // hubsize, directed
////      shakeTowards: 'roots', // roots, leaves
////      direction: 'UD' // UD, DU, LR, RL
////    }
//  }
}
const network = new vis.Network(container, data, options);

window.graph = network