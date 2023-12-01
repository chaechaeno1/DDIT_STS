<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<!-- tui css js 설정 -->
<link rel="stylesheet" href="https://uicdn.toast.com/grid/latest/tui-grid.css" />
<script src="https://uicdn.toast.com/grid/latest/tui-grid.js"></script>

<script type="text/javascript">

const grid = new tui.Grid({
    el: document.getElementById('grid'),
    data: {
      api: {
        readData: { url: '/api/readData', method: 'GET' }
      }
    },
    scrollX: false,
    scrollY: false,
    minBodyHeight: 30,
    rowHeaders: ['rowNum'],
    pageOptions: {
      perPage: 5
    },
    columns: [
      {
        header: 'Name',
        name: 'name'
      },
      {
        header: 'Artist',
        name: 'artist'
      },
      {
        header: 'Type',
        name: 'type'
      },
      {
        header: 'Release',
        name: 'release'
      },
      {
        header: 'Genre',
        name: 'genre'
      }
    ]
  });


</script>

<body>

<div id="grid"></div>

</body>
</html>