const bridge = {
    addNode: (id) => {
        nodes.add({id, label: id})
    },
    addEdge: (from, to) => {
        edges.add({from, to})
    },
    selectedGraphId: ''
}
window.GraphViewJs = bridge
