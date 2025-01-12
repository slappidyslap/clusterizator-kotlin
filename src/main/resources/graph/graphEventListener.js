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

function stringToColor(input, isDarkTheme) {
  // Хэшируем строку в число
  const hash = hashString(input)

  // Преобразуем хэш в цвет (RGB)
  const r = (hash & 0xFF0000) >> 16;
  const g = (hash & 0x00FF00) >> 8;
  const b = hash & 0x0000FF;

  const factor = isDarkTheme ? 6 : 1
  // Применяем пастельный эффект для тёмной темы (увеличиваем яркость, снижаем насыщенность)
  const pastelR = Math.floor((r + 128) / factor);
  const pastelG = Math.floor((g + 128) / factor);
  const pastelB = Math.floor((b + 128) / factor);

  return `rgba(${pastelR}, ${pastelG}, ${pastelB}, 0.2)`
}

graph.on('click', e => {
  if (e.nodes.length) {
    const nodeId = e.nodes[0]
    const bgColor = stringToColor(nodeId, false)
    document.getElementById("graph").style.backgroundColor = bgColor

    GraphView.selectNode(nodeId)
  } else {
    document.getElementById("graph").style.backgroundColor = ''
    GraphView.deselectNode()
  }
})
