const bridge = {
    addNode: (id) => {
        nodes.add({id, label: id})
    },
    addEdge: (from, to) => {
        edges.add({from, to})
    }
}
window.GraphViewJs = bridge
