function getRandomPastelColor() {
    const r = Math.floor((Math.random() * 128) + 127); // Компонент красного (127-255)
    const g = Math.floor((Math.random() * 128) + 127); // Компонент зеленого (127-255)
    const b = Math.floor((Math.random() * 128) + 127); // Компонент синего (127-255)
    const alpha = 0.5; // Низкая прозрачность
    return `rgba(${r}, ${g}, ${b}, ${alpha})`;
}

graph.on('selectNode', e => {
  document.getElementById("graph").style.backgroundColor = getRandomPastelColor()
  GraphView.selectEdge(e.nodes[0])
});

network.on('dragEnd', function(){
  graph.unselectAll();
});

network.on('dragEnd', function(){
  graph.unselectAll();
});