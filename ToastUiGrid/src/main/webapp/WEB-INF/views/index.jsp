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

<body>


	<h3 align="center">학 생 평 가</h3>
	<div id="grid"></div>


</body>

<script>

const dataSource = {
		  api: {
		    readData: { url: '/api/readData', method: 'GET' },
		    createData: { url: '/api/createData', method: 'POST' },
		    updateData: { url: '/api/updateData', method: 'PUT' },
		    modifyData: { url: '/api/modifyData', method: 'PUT' },
		    deleteData: { url: '/api/deleteData', method: 'DELETE' }
		  }
		}; //dataSource 끝


      // AJAX를 이용하여 서버에 데이터 전송
      $.ajax({
          type: 'POST',
          url: '/grid/saveData',
          contentType: 'application/json',
          dataType : "JSON",
          data: dataSource,
          success: function (result) {
              alert(result); // 서버에서 반환한 메시지 표시
              
              const grid = new tui.Grid({
            	    el: document.getElementById('grid'),
            	    scrollX: false,
            	    scrollY: false,
            	    columns: [
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
            	              { text: '★★★★★', value: '4' },
            	              { text: '★★★★★', value: '5' }
            	            ]
            	          }
            	        }
            	      }
            	    ]
            	  }); //grid 끝!

             	 grid.resetData(result); 
            	  
          }, //success 끝

      }); //ajax 끝
      

  

	
</script>







</html>