window.addNode = (id) => {
  nodes.add({id, label: id})
}

window.addEdge = (from, to) => {
  edges.add({from, to})
}