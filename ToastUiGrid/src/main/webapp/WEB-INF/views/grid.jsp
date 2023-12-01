<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>조원 평가 페이지</title>

<!-- tui css js 설정 -->
<link rel="stylesheet" href="https://uicdn.toast.com/grid/latest/tui-grid.css" />
<script src="https://uicdn.toast.com/grid/latest/tui-grid.js"></script>
</head>



<body>

	<h3 align="center">학 생 평 가</h3>
	<div id="grid"></div>



</body>


<script>


 var grid = new tui.Grid({
    el: document.getElementById('grid'),
    scrollX: false,
    scrollY: false,
    columns: [
      {//수정X
        header: '번호',
        name: 'id',
      },
      {//수정X
        header: '이름',
        name: 'name',
      },
      {//수정X
        header: '나이',
        name: 'age',
      },
      {//수정O
        header: '연락처',
        name: 'tel',
        editor: 'text'
      },
      {//select, 전공자 유무
          header: '전공자 유무',
          name: 'majorType',
          formatter: 'listItemText',
          editor: {
            type: 'select',
            options: {
              listItems: [
                { text: '전공자', value: '1' },
                { text: '비전공자', value: '2' }
             ]
           }
         }
       },
       {
         header: '자격증',
         name: 'certificate',
         formatter: 'listItemText',
         editor: {
           type: 'checkbox',
           options: {
             listItems: [
               { text: '정보처리기사', value: '1' },
               { text: 'SQLD', value: '2' },
               { text: '빅데이터분석기사', value: '3' },
               { text: '정보처리기능사', value: '4' },
               { text: '정보처리산업기사', value: '5' },
               { text: '-', value: '6' }
             ]
           }
         },      
        copyOptions: {
          useListItemText: true // when this option is used, the copy value is concatenated text
        }
      },
      {
        header: '평가',
        name: 'grade',
        copyOptions: {
          useListItemText: true
        },
        formatter: 'listItemText',
        editor: {
          type: 'radio',
          options: {
            listItems: [
              { text: '★☆☆☆☆', value: '1' },
              { text: '★★☆☆☆', value: '2' },
              { text: '★★★☆☆', value: '3' },
              { text: '★★★★☆', value: '4' },
              { text: '★★★★★', value: '5' }
            ]
          }
        }
      }
    ]
  });
  
  var gridData = [
		{
			name: '송시운',
			age: '26세',
			tel: '010-1111-3333',
			majorType: '2',
			certificate: ['1','3'],
			grade: '3'
		},
		{
			name: '강진석',
			age: '20세',
			tel: '010-1111-2222',
			majorType: '1',
			certificate: ['1','3'],
			grade: '3'
			
		},
		{
			name: '김민채',
			age: '26세',
			tel: '010-1111-3333',
			majorType: '2',
			certificate: ['1','3'],
			grade: '3'
		},
		{
			name: '김영진',
			age: '20세',
			tel: '010-1111-2222',
			majorType: '1',
			certificate: ['1','3'],
			grade: '3'
			
		},
		{
			name: '서강민',
			age: '26세',
			tel: '010-1111-3333',
			majorType: '2',
			certificate: ['1','3'],
			grade: '3'
		},
		{
			name: '정소현',
			age: '20세',
			tel: '010-1111-2222',
			majorType: '1',
			certificate: ['1','3'],
			grade: '3'
			
		},
	];
  
  tui.Grid.applyTheme('striped', { // 테마 설정
	    cell: {
	        head: {
	            background: '#eef'
	        },
	        evenRow: {
	            background: '#fee'
	        }
	    }
	});
  
 
  
  grid.resetData(gridData);
  
  
  
//2. 서버 측에서 값 저장하기:
//	  서버 측에서 값 저장은 클라이언트에서 수정된 데이터를 서버로 전송하여 데이터베이스에 저장하는 방식입니다. 클라이언트는 수정된 데이터를 서버에 전송하고, 서버는 해당 데이터를 처리하여 데이터베이스에 반영합니다.  


//클라이언트에서 값이 수정되면! 
//TOAST UI Grid의 beforeSave 이벤트를 통해 수정된 데이터를 서버로 전송하는 방법
grid.on('beforeSave', function (ev) {
  var modifiedData = ev.modifiedData;

  // 서버로 데이터를 전송
  $.ajax({
    url: '/gird/gird01', // 서버의 엔드포인트
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(modifiedData),
    success: function (response) {
      // 서버의 응답을 처리
      grid.refreshLayout();
    },
    error: function (error) {
      console.error('Error saving data on the server:', error);
    }
  });
});

  
  


	
</script>




</html>