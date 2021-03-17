function confirm_email(){
	var yncheck = $('.confirmyn').val();
	if(yncheck=="n"){
		msg = "email 중복검사를 하세요!"
			$('.modal').modal('show');
	}
}

function confirm_empno(){
	var yncheck = $('.confirmyn').val();
	if(yncheck=="n"){
		msg = "사원번호 중복검사를 하세요!"
			$('.modal').modal('show');
	}
}

function indexReload(){
	window.location.reload();
}

tinymce.init({
      selector: 'textarea',
      plugins: 'a11ychecker advcode casechange formatpainter linkchecker autolink lists checklist media mediaembed pageembed permanentpen powerpaste table advtable tinycomments tinymcespellchecker',
      toolbar: 'addcomment showcomments casechange checklist code formatpainter table',
      toolbar_mode: 'floating',
      tinycomments_mode: 'embedded',
      tinycomments_author: 'Author name',
	  width: '100%',
	  branding: false
   });

window.onload = function(){
    $('.ui.dropdown').dropdown();
};

function zipcodeFind(){

  new daum.Postcode({
            oncomplete: function(data) {
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수
                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }
                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if(data.userSelectedType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zipcode').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('addr1').value = fullAddr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('addr2').focus();
            }
        }).open();
 }

function comonFinal(thistext){
	 	message ="";
	    message += "<span style='color:#ff0000'<B>"+thistext+"</B></span>";
	    message += "<span style='color:#000000'> 입니다.</span><br><br>";
	    message += "<span style='color:#000000'>  [마감] </span>";
		message += "<span style='color:#ff0000'> 클릭시 데이터는 복구</span>"
		message += "<span style='color:#000000'> 되지않습니다.</span>";
    return message;
}

$(document).ready(function(){
	
	$('.ui.simple.dropdown.item').dropdown();
	
	$('.confirm').on('click', function(){
		var email = $('#email').val();
		if(email == ""){
			$('.description').text("E-mail을 입력하세요!");
			$('.modal').modal('show');
			return;
		}else{
			var email = email;
			$.ajax({
	            type:'POST',
	            data : {email:email},
	            datatype:'json',
	            url : 'emailConfirmAjax',
	            contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
	            success : function(data){
	            	var msg="";
	            	if(data == "y"){
	            		msg = "사용중인 email입니다!"
            			$('.confirmyn').val('n');
            			$('.description').text(msg);
		    			$('.modal').modal('show');
		    			$('#email').val('');
		    			$('#email').focus();
	            	}else{
	            		$('.confirmyn').val('y');
	            		msg = "사용 가능한 email입니다!"
            			$('.description').text(msg);
		    			$('.modal').modal('show');
	            	}
	            	
	            },
	            error : function(xhr, status, error){
	                alert('ajax error'+xhr.status);
	            }
        	});
		  }
		  $('.ui.black.deny.button').modal('hide');
	});
	
	$('#viewphoto').on('click',function(){
		$('input[type=file]').click();
		return false;
	});
	
	$('#imgfile').on('change',function(event){
		var imgpath = URL.createObjectURL(event.target.files[0]);
		$('#viewphoto').attr('src',imgpath);
	});
	
	$('#memberexample').DataTable( {
        deferRender:    true,
        autoWidth: 		false,
        scrollY:        500,
        scrollCollapse: true,
        scroller:       true,
        language: { search: "" }
    });

	$('#salaryexample').DataTable( {
        deferRender:    true,
        autoWidth: 		true,
        scrollY:        500,
        scrollCollapse: true,
        scroller:       true,
        language: { search: "" }
    } );
	
	$('#salaryrollexample').DataTable( {
		aaSorting: [],
        deferRender:    true,
        autoWidth: 		true,
        scrollY:        500,
        scrollCollapse: true,
        scroller:       true,
        language: { search: "" }
    } );

	$('#productexample').DataTable( {
		aaSorting: [],
        deferRender:    true,
        autoWidth: 		true,
        scrollY:        500,
        scrollCollapse: true,
        scroller:       true,
        language: { search: "" }
    } );
	
	$('#venderexample').DataTable( {
		aaSorting: [],
        deferRender:    true,
        autoWidth: 		true,
        scrollY:        500,
        scrollCollapse: true,
        scroller:       true,
        language: { search: "" }
    } );

	$('#balanceexample').DataTable( {
		aaSorting: [],
        deferRender:    true,
        autoWidth: 		true,
        scrollY:        500,
        scrollCollapse: true,
        scroller:       true,
        language: { search: "" }
    } );
	
	$(document).on('click','#memberexample td #editbtn',function(){
		var row = $(this).closest('tr');
		var td = row.children();
		var email = td.eq(1).text();
		var level = td.eq(4).children().val();
		
		$.ajax({
            type:'POST',
            data : {email:email,level:level},
            datatype:'json',
            url : 'memberUpdateAjax',
            contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
            success : function(data){
            	if(data=="y"){
            		$('#resultmessage').text("수정 되었습니다.");
            	}else{
            		$('#resultmessage').text("수정 되지 않았습니다.");
            	}
            	
            	$('#successmessage').css('display',"block")
            	.delay(1200).queue(function(){
            		$('#successmessage').css('display',"none").dequeue();
            	});
            },
            error : function(xhr, status, error){
                alert('ajax error'+xhr.status);
            }
    	});
	});
	
	
	$(document).on('click','#productexample td #producteditbtn',function(){
		var row = $(this).closest('tr');
		var td = row.children();
		var code = td.eq(0).text();
		var stock = td.eq(17).children().val();
		$.ajax({
            type:'POST',
            data : {code:code,stock:stock},
            datatype:'json',
            url : 'productUpdateAjax',
            contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
            success : function(data){
            	if(data=="y"){
            		$('#resultmessage').text("수정 되었습니다.");
            	}else{
            		$('#resultmessage').text("수정 되지 않았습니다.");
            	}
            	
            	$('#successmessage').css('display',"block")
            	.delay(1200).queue(function(){
            		$('#successmessage').css('display',"none").dequeue();
            	});
            },
            error : function(xhr, status, error){
                alert('ajax error'+xhr.status);
            }
    	});
	});
	
	$(document).on('click','#productexample td #productdeletebtn',function(){
		var row = $(this).closest('tr');
		var td = row.children();
		var code = td.eq(0).text();
		$('.ui.mini.modal.productdeletemodal').modal('show');
		$('#producedeleteok').on('click',function(){
			$.ajax({
	            type:'POST',
	            data : {code:code},
	            datatype:'json',
	            url : 'productDeleteAjax',
	            contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
	            success : function(data){
	            	if(data=="y"){
	            		row.remove();
	            		$('#resultmessage').text("삭제 되었습니다.");
	            	}else{
	            		$('#resultmessage').text("삭제 되지 않았습니다.");
	            	}
	            	
	            	$('#successmessage').css('display',"block")
	            	.delay(1200).queue(function(){
	            		$('#successmessage').css('display',"none").dequeue();
	            	});
	            	$('.ui.mini.modal.productdeletemodal').modal('hide');
	            },
	            error : function(xhr, status, error){
	                alert('ajax error'+xhr.status);
	            }
	    	});
			$('#productdeletecancel').on('click',function(){
				alert('cancel');
				$('.ui.mini.modal.productdeletemodal').modal('hide');
			});

		});
	});
	 
	$(document).on('click','#salaryexample td #salaryeditbtn',function(){
		
		var row = $(this).closest('tr');
		var td = row.children();
		var empno = td.eq(0).text();
		var yn = td.eq(10).children().val();
		$.ajax({
            type:'POST',
            data : {empno:empno,yn:yn},
            datatype:'json',
            url : 'salaryUpdateAjax',
            contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
            success : function(data){
            	if(data=="y"){
            		$('#resultmessage').text("수정 되었습니다.");
            	}else{
            		$('#resultmessage').text("수정 되지 않았습니다.");
            	}
            	
            	$('#successmessage').css('display',"block")
            	.delay(1200).queue(function(){
            		$('#successmessage').css('display',"none").dequeue();
            	});
            },
            error : function(xhr, status, error){
                alert('ajax error'+xhr.status);
            }
    	});
	});
	
	$('.attachbtn').on('click',function(){
		$('#b_attach').click();
		$('#b_attach').change(function() {
	        var filename = $('#b_attach').val();
			$('.b_attachname').attr('value',filename);
	    });
	});	
	
	$(document).on('click','#memberexample td #deletebtn',function(){
		var row = $(this).closest('tr');
		var td = row.children();
		var email = td.eq(1).text();
		
		$('.ui.mini.modal.delete').modal('show');
		
		$('#deleteok').on('click',function(){
			$.ajax({
	            type:'POST',
	            data : {email:email},
	            datatype:'json',
	            url : 'memberDeleteAjax',
	            contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
	            success : function(data){
	            	if(data=="y"){
	            		row.remove();
	            		$('#resultmessage').text("삭제 되었습니다.");
	            	}else{
	            		$('#resultmessage').text("삭제 되지 않았습니다.");
	            	}
	            	
	            	$('#successmessage').css('display',"block")
	            	.delay(1200).queue(function(){
	            		$('#successmessage').css('display',"none").dequeue();
	            	});
	            	$('.ui.mini.modal.delete').modal('hide');
	            },
	            error : function(xhr, status, error){
	                alert('ajax error'+xhr.status);
	            }
	    	});
		});
		
		$('#deletecancel').on('click',function(){
			$('.ui.mini.modal.delete').modal('hide');
		});
		
	});
	
	$(document).on('click','#salaryexample td #salarydeletebtn',function(){
		var row = $(this).closest('tr');
		var td = row.children();
		var empno = td.eq(0).text();
		
		$('.ui.mini.modal.salarydelete').modal('show');
		$('#salarydeleteok').on('click',function(){
			$.ajax({
	            type:'POST',
	            data : {empno:empno},
	            datatype:'json',
	            url : 'salaryDeleteAjax',
	            contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
	            success : function(data){
	            	if(data=="y"){
	            		row.remove();
	            		$('#resultmessage').text("삭제 되었습니다.");
	            	}else{
	            		$('#resultmessage').text("삭제 되지 않았습니다.");
	            	}
	            	
	            	$('#successmessage').css('display',"block")
	            	.delay(1200).queue(function(){
	            		$('#successmessage').css('display',"none").dequeue();
	            	});
	            	$('.ui.mini.modal.salarydelete').modal('hide');
	            },
	            error : function(xhr, status, error){
	                alert('ajax error'+xhr.status);
	            }
	    	});
		});
		
		$('#salarydeletecancel').on('click',function(){
			$('.ui.mini.modal.salarydelete').modal('hide');
		});
		
	});
	
	
	$(document).on('click','#balanceexample td #balanceeditbtn',function(){
		
		var row = $(this).closest('tr');
		var td = row.children();
		var yyyy = td.eq(0).text();
		var vendcode = td.eq(1).text();
		var balance = td.eq(6).children().val();
		$.ajax({
            type:'POST',
            data : {yyyy:yyyy,vendcode:vendcode,balance:balance},
            datatype:'json',
            url : 'balanceUpdateAjax',
            contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
            success : function(data){
            	if(data=="y"){
            		$('#resultmessage').text("수정 되었습니다.");
            	}else{
            		$('#resultmessage').text("수정 되지 않았습니다.");
            	}
            	
            	$('#successmessage').css('display',"block")
            	.delay(1200).queue(function(){
            		$('#successmessage').css('display',"none").dequeue();
            	});
            },
            error : function(xhr, status, error){
                alert('ajax error'+xhr.status);
            }
    	});
	});
	
	$(document).on('click','#balanceexample td #balancedeletebtn',function(){
		var row = $(this).closest('tr');
		var td = row.children();
		var yyyy = td.eq(0).text();
		var vendcode = td.eq(1).text();
		$('.ui.mini.modal.balancedeletemodal').modal('show');
		 
		$('#balancemodalcancel').on('click',function(){
			$('.ui.mini.modal.balancedeletemodal').modal('hide');
			return;
		});
		$('#balancemodaldelete').on('click',function(){
			$.ajax({
	            type:'POST',
	            data : {yyyy:yyyy,vendcode:vendcode},
	            datatype:'json',
	            url : 'balanceDeleteAjax',
	            contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
	            success : function(data){
	            	if(data=="y"){
	            		row.remove();
	            		$('#resultmessage').text("삭제 되었습니다.");
	            	}else{
	            		$('#resultmessage').text("삭제 되지 않았습니다.");
	            	}
	            	
	            	$('#successmessage').css('display',"block")
	            	.delay(1200).queue(function(){
	            		$('#successmessage').css('display',"none").dequeue();
	            	});
	            	$('.ui.mini.modal.salarydelete').modal('hide');
	            },
	            error : function(xhr, status, error){
	                alert('ajax error'+xhr.status);
	            }
	    	});
			
			$('.ui.mini.modal.balancedeletemodal').modal('hide');
			return;
		});
		
	});
	
	
	$('.boarddelete').on('click',function(){
		$('.ui.mini.modal.boardmodal').modal('show');
		
		$('.modalcancel').on('click',function(){
			$('.ui.mini.modal.boardmodal').modal('hide');
			return;
		});
		$('.modaldelete').on('click',function(){
			$('.ui.mini.modal.boardmodal').modal('hide');
			var b_seq = $('#hidden_seq').val();
			document.location.href="boardDelete?b_seq="+b_seq;
		});
	});
	
	
	$('.empnoconfirm').on('click', function(){
		var empno = $('#empno').val();
		if(empno == ""){
			$('.description').text("사원번호를 입력하세요!");
			$('.modal').modal('show');
			return;
		}else{
			$.ajax({
	            type:'POST',
	            data : {empno:empno},
	            datatype:'json',
	            url : 'salaryConfirmAjax',
	            contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
	            success : function(data){
	            	var msg="";
	            	if(data == "y"){
	            		msg = "사용중인 사원번호입니다!"
            			$('.confirmyn').val('n');
            			$('.description').text(msg);
		    			$('.modal').modal('show');
		    			$('#empno').val('');
		    			$('#empno').focus();
	            	}else{
	            		$('.confirmyn').val('y');
	            		msg = "사용 가능한 사원번호입니다!"
            			$('.description').text(msg);
		    			$('.modal').modal('show');
	            	}
	            	
	            },
	            error : function(xhr, status, error){
	                alert('ajax error'+xhr.status);
	            }
        	});
		  }
		  $('.ui.black.deny.button').modal('hide');
	});
	
	$('.salarytaxbtn').on('click',function(){
		$('.description').text("계산 버턴을 누르면 기존 데이터는 삭제되고   다시 생성됩니다 !");
		$('#salaryrollcreateok').text("계산");
		$('.ui.mini.modal.salaryrollcreate').modal('show');
		
		$('#salaryrollcreateok').on('click',function(){
			$('#salaryTexForm').attr('action','salaryTaxSubmit');
			$('#salaryTexForm').submit();
		});
		$('#salaryrollcreatecancel').on('click',function(){
			$('.ui.mini.modal.salaryrollcreate').modal('hide');
			return;
		});
	});
	
	$('.salarydeletebtn').on('click',function(){
		$('.description').text("데이터를 삭제 하시겠습니까?");
		$('#salaryrollcreateok').text("삭제");
		$('.ui.mini.modal.salaryrollcreate').modal('show');
		
		$('#salaryrollcreateok').on('click',function(){
			$('#salaryTexForm').attr('action','salaryDeleteSubmit');
			$('#salaryTexForm').submit();
		});
		$('#salaryrollcreatecancel').on('click',function(){
			$('.ui.mini.modal.salaryrollcreate').modal('hide');
			return;
		});
	});
	
	$('#selectproduct').on('change',function(){
		var code = $('#selectproduct').val();
		$.ajax({
            type:'POST',
            data : {code:code},
            datatype:'json',
            url : 'productSelectedAjax',
            contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
            success : function(data){
				$('#price').val(data.buyprice);
				$('#proname').val(data.name);
				$('#qty').focus();
            },
            error : function(xhr, status, error){
                alert('ajax error'+xhr.status);
            }
    	});
	});
	
	$('#selectvender').on('change',function(){
		var vendcode = $('#selectvender').val();
		$.ajax({
            type:'POST',
            data : {vendcode:vendcode},
            datatype:'json',
            url : 'buyNew',
            contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
            success : function(data){
				$('#rightvendername').val(data.vendname);
				$('#vendname').val(data.vendname);
				$('#yyyy').val(data.yyyy);
				$('#mm').val(data.mm);
				$('#dd').val(data.dd);
				$('#no').val(data.no);
				$('#hang').val(data.hang);
            },
            error : function(xhr, status, error){
                alert('ajax error'+xhr.status);
            }
    	});
	});
	
	$('#qty').keyup(function(){
		var price = $('#price').val();
		var qty = $(this).val();
		total = price * qty;
		$('#total').val(total);
	});
	
	$('#buysavebtn').on('click',function(){
		var vendcode = $('#selectvender').val();
		if(vendcode == "0000"){
			$('.description').text("거래처를 선택하세요!");
			$('.ui.mini.modal.buysavemodal').modal('show');
		
			$('#buymodalsavebtn').on('click',function(){
				$('.ui.mini.modal.buysavemodal').modal('hide');
				return;
			});
			return;
		}
		
		var code = $('#selectproduct').val();
		if(code == "0000000000000"){
			$('.description').text("상품을 선택하세요!");
			$('.ui.mini.modal.buysavemodal').modal('show');
		
			$('#buymodalsavebtn').on('click',function(){
				$('.ui.mini.modal.buysavemodal').modal('hide');
				return;
			});
			return;
		}
		$('#buyInsertSave').attr('action','buyInsertSave');
		$('#buyInsertSave').submit();
	});
	
	$('#findbtnclick').on('click',function(){
		var vendcode = $('#selectvenderfind').val();
		if(vendcode == "0000"){
			$('.description').text("거래처를 선택하세요!");
			$('.ui.mini.modal.buysavemodal').modal('show');
		
			$('#buymodalsavebtn').on('click',function(){
				$('.ui.mini.modal.buysavemodal').modal('hide');
				return;
			});
			return;
		}
		$('#buyfindform').submit();
	});
	
	$('#selectvenderfind').on('change',function(){
		var vendname = $('#selectvenderfind option:selected').text();
		$('#hiddenvendname').val(vendname);
	});
	
	var publicselectedrow;
	
	$("#buyfindtable").on("click", "#buyitemrow", "td", function() {
		var row = $(this).closest('tr');
		publicselectedrow = row;
		var td = row.children();
		var seq = td.eq(0).text();
		$.ajax({
            type:'POST',
            data : {seq:seq},
            datatype:'json',
            url : 'buyRowItemSelectedAjax',
            contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
            success : function(data){
				$('#hiddenseq').val(data.seq);
				$('#selectvender').val(data.vendcode).attr("selected","selected");
				$('#selectproduct').val(data.procode).attr("selected","selected");
				$('#vendname').val(data.vendname);
				$('#proname').val(data.proname);
				$('#yyyy').val(data.yyyy);
				$('#mm').val(data.mm);
				$('#dd').val(data.dd);
				$('#no').val(data.no);
				$('#hang').val(data.hang);
				$('#beforeprocode').val(data.procode);
				$('#price').val(data.price);
				$('#beforeprice').val(data.price);
				$('#qty').val(data.qty);
				$('#beforeqty').val(data.qty);
				$('#total').val(data.total);
				$('#beforetotal').val(data.total);
				$('#memo').val(data.memo);
				
				$('#buysaveupdateGroupsave').css('display','none');
				$('#buysaveupdateGroupupdate').css('display','block');
            },
            error : function(xhr, status, error){
                alert('ajax error'+xhr.status);
            }
    	});
	});
	
	$('#buyupdatebtn').on('click',function(){
		var seq=$('#hiddenseq').val();
		var vendcode=$('#selectvender').val();
		var vendname=$('#vendname').val();
		var procode=$('#selectproduct').val();
		var proname=$('#proname').val();
		var beforeprocode=$('#beforeprocode').val();
		var yyyy=$('#yyyy').val();
		var mm=$('#mm').val();
		var dd=$('#dd').val();
		var no=$('#no').val();
		var hang=$('#hang').val();
		var beforeprice=$('#beforeprice').val();
		var price=$('#price').val();
		var qty=$('#qty').val();
		var beforeqty=$('#beforeqty').val();
		var total=$('#total').val();
		var beforetotal=$('#beforetotal').val();
		var memo=$('#memo').val();
		
		var obj = {"seq":seq,"yyyy":yyyy,"mm":mm,"dd":dd,"no":no,"hang":hang,"beforeprocode":beforeprocode,
		"vendcode":vendcode,"procode":procode,"vendname":vendname,"proname":proname,
		"beforeprice":beforeprice,"price":price,"qty":qty,"beforeqty":beforeqty,
		"total":total,"beforetotal":beforetotal,"memo":memo};
		
		$.ajax({
            type:'POST',
            data:JSON.stringify(obj),
            datatype:'json',
            url : 'buyUpdateJsonAjax',
            contentType: 'application/json',
            success : function(data){
				var td = publicselectedrow.children();
				td.eq(3).text(data.proname);
				td.eq(4).text(data.price);
				td.eq(5).text(data.qty);
				td.eq(6).text(data.total);
				
				$('#resultmessage').text("수정 되었습니다.");
				$('#successmessage').css('display',"block")
            	.delay(1200).queue(function(){
            		$('#successmessage').css('display',"none").dequeue();
            	});
            },
            error : function(xhr, status, error){
                alert('ajax error'+xhr.status);
            }
    	});
	});
	
	$('#buydeletebtn').on('click',function(){
		$('.ui.mini.modal.buydeletemodal').modal('show');
		
		$('#buymodaldeletebtn').on('click',function(){
			var seq = publicselectedrow.children().eq(0).text();
			
			$.ajax({
	            type:'POST',
	            data : {seq:seq},
	            datatype:'json',
	            url : 'buyRowItemDeleteAjax',
	            contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
	            success : function(data){
					 publicselectedrow.remove();
	            },
	            error : function(xhr, status, error){
	                alert('ajax error'+xhr.status);
	            }
	    	});

			$('.ui.mini.modal.buydeletemodal').modal('hide'); 
		});
		
		$('#buymodaldeletecancelbtn').on('click',function(){
			$('.ui.mini.modal.buydeletemodal').modal('hide'); 
		});
	});
	
	$('.finalyyyy').on('click',function(){
		var rtnmessage = comonFinal($(this).text());
		$('.description').empty();
		$('.description').append(rtnmessage);
		$('.ui.mini.modal.finalmodal').modal('show');
		
		$('#finalmodalok').on('click',function(){
			$('#finalform').attr('action','yyyyFinal');
			$('#finalform').submit();
		});
		
		$('#finalmodalcancel').on('click',function(){
			$('.ui.mini.modal.finalmodal').modal('hide'); 
		});
	});
	
	$('.finalmm').on('click',function(){
		var rtnmessage = comonFinal($(this).text());
		$('.description').empty();
		$('.description').append(rtnmessage);
		$('.ui.mini.modal.finalmodal').modal('show');
		
		$('#finalmodalok').on('click',function(){
			$('#finalform').attr('action','mmFinal');
			$('#finalform').submit();
			$('.ui.mini.modal.finalmodal').modal('hide'); 
		});
		
		$('#finalmodalcancel').on('click',function(){
			$('.ui.mini.modal.finalmodal').modal('hide'); 
		});
	});
	
	$('.finaldd').on('click',function(){
		var rtnmessage = comonFinal($(this).text());
		$('.description').empty();
		$('.description').append(rtnmessage);
		$('.ui.mini.modal.finalmodal').modal('show');
		
		$('#finalmodalok').on('click',function(){
			$('#finalform').attr('action','ddFinal');
			$('#finalform').submit();
			$('.ui.mini.modal.finalmodal').modal('hide'); 
		});
		
		$('#finalmodalcancel').on('click',function(){
			$('.ui.mini.modal.finalmodal').modal('hide'); 
		});
	});
	
	$('.ui.floating.dropdown.labeled.search.icon.button').dropdown({
	    onChange: function (lang) {
			alert();
	    },
	    forceSelection: false, 
	    selectOnKeydown: false, 
	    showOnFocus: false,
	    on: "hover" 
	});
});

