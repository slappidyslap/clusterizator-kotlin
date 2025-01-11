/*function getRandomPastelColor() {
    const r = Math.floor((Math.random() * 128) + 127);
    const g = Math.floor((Math.random() * 128) + 127);
    const b = Math.floor((Math.random() * 128) + 127);
    const alpha = 0.5;
    return `rgba(${r}, ${g}, ${b}, ${alpha})`;
}*/

// Темная тема
function getRandomPastelColor() {
    const r = Math.floor((Math.random() * 64));
    const g = Math.floor((Math.random() * 64));
    const b = Math.floor((Math.random() * 64));
    const alpha = 0.2;
    return `rgba(${r}, ${g}, ${b}, ${alpha})`;
}

function hashString(input) {
  let hash = 0;
  for (let i = 0; i < input.length; i++) {
    hash = input.charCodeAt(i) + ((hash << 5) - hash);
  }
  return hash
}

function stringToColorForDarkTheme(input) {
  // Хэшируем строку в число
  const hash = hashString(input)

  // Преобразуем хэш в цвет (RGB)
  const r = (hash & 0xFF0000) >> 16;
  const g = (hash & 0x00FF00) >> 8;
  const b = hash & 0x0000FF;

  // Применяем пастельный эффект для тёмной темы (увеличиваем яркость, снижаем насыщенность)
  const pastelR = Math.floor((r + 128) / 6);
  const pastelG = Math.floor((g + 128) / 6);
  const pastelB = Math.floor((b + 128) / 6);

  return {
    value: `rgba(${pastelR}, ${pastelG}, ${pastelB}, 0.2)`,
    r: pastelR,
    g: pastelG,
    b: pastelB
  }
}

graph.on('selectNode', e => {
  const nodeId = e.nodes[0]
  const bgColor = stringToColorForDarkTheme(nodeId)
  document.getElementById("graph").style.backgroundColor = bgColor.value

  GraphViewJs.selectedGraphId = nodeId
  GraphView.selectNode(nodeId)
});

graph.on('deselectNode', e => {
  document.getElementById("graph").style.backgroundColor = ''
  GraphViewJs.selectedGraphId = ''
  GraphView.deselectNode()
})
