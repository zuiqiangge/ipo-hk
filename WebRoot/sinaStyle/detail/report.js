var textT="<li><div class='text tool tools'><ul class='control'><!--<li><b title='�ƶ�' id='moveBtn' class='icon-arrows'></b></li> --><li><b title='�༭' id='editorBtn' class='icon-pencil' style='display: none;'></b></li><li><b title='ȷ��' id='okBtn' class='icon-check'></b></li><li><b title='ɾ��' id='deleteBtn' class='icon-trash-o'></b></li></ul><div contenteditable='true'  class='textCon contenteditable texteditor' id='contentelement' placeholder='�ı�'></div></div></li>";

/*
var trendF="<li><div class='float_l'><span class='line'></span><span class='round'></span><span class='line'></span></div><div class='sz'></div><div class='text text2'><i class='icon-arrows yb-move' style='display: none;'></i><i class='icon-trash-o yb-delete'></i><i class='icon-control_point yb-add yb-addF'></i><i class='icon-control_point yb-add yb-addT' style='display: none;'></i><div class='contenteditable' contenteditable='false'>[1��1��] �¼����ݣ���Դ��վ��ת������0</div></div></li>";
*/

var trendF="<li><div class='float_l'><span class='line'></span><span class='round'></span><span class='line'></span></div><div class='sz'></div><div class='text text2'><i class='icon-arrows yb-move' style='display: none;'></i><i class='icon-trash-o yb-delete'></i><i class='icon-control_point yb-add yb-addF'></i><i class='icon-control_point yb-add yb-addT' style='display: none;'></i><div class='contenteditable' contenteditable='false'>[1��1��] �¼����ݣ���Դ��վ��ת������0</div></div></li>";

//var trendT="<li><div class='float_l'><span class='line'></span><span class='round'></span><span class='line'></span></div><div class='sz'></div><div class='text text2'><i class='icon-arrows yb-move'></i><i class='icon-trash-o yb-delete'></i><i class='icon-control_point yb-add yb-addF' style='display: none;'></i><i class='icon-control_point yb-add yb-addT'></i><div class='contenteditable' contenteditable='true'>[1��1��] �¼����ݣ���Դ��վ��ת������0</div></div></li>";

var trendT="<li><div class='float_l'><span class='line'></span><span class='round'></span><span class='line'></span></div><div class='sz'></div><div class='text text2'><i class='icon-arrows yb-move'></i><i class='icon-trash-o yb-delete' onclick='del(this);'></i><i class='icon-control_point yb-add yb-addF' onclick='add();'></i><i class='icon-control_point yb-add yb-addT' onclick='add();'></i><div class='contenteditable' contenteditable='true'>[1��1��] �¼����ݣ���Դ��վ��Ӱ������0</div></div></li>";

var hotTableF="<tr><td style='text-align: left;'><div contenteditable='false' class='contenteditable'>����<p class='phone_show time'></p></div></td><td><div contenteditable='false' class='contenteditable'>��Դվ��</div></td><td class='phone_hide'><div contenteditable='false' class='contenteditable'>ʱ��</div></td><td><div contenteditable='false' class='contenteditable'>ת����</div></td><td><i class='icon-control_point td-add td-addF'></i><i class='icon-control_point td-add td-addT' style='display: none;'></i><i class='icon-trash-o td-delete'></i></td></tr>";

var hotTableT="<tr><td style='text-align: left;'><div contenteditable='true' class='contenteditable'>����<p class='phone_show time'></p></div></td><td><div contenteditable='true' class='contenteditable'>��Դվ��</div></td><td class='phone_hide'><div contenteditable='true' class='contenteditable'>ʱ��</div></td><td><div contenteditable='true' class='contenteditable'>ת����</div></td><td><i class='icon-arrows td-move'></i> <i class='icon-control_point td-add td-addF' style='display: none;'></i><i class='icon-control_point td-add td-addT'></i><i class='icon-trash-o td-delete'></i></td></tr>";


//��Ŀ����Ч��
$(function() {
    $( ".reportBox" ).sortable({
        connectWith: ".reportBox",
        handle: ".moveBtn",
        placeholder: "portlet-placeholder ui-corner-all"
    });
    $('.reportBox').bind('sortstop', function(event, ui) {
        var $this=ui.item;
        /*var thisId=$this.attr("id");
        var upId=$this.prev().attr("id");*/
        var thisName=$this.attr("name");
        var upName=$this.prev().attr("name");
        var nextName=$this.next().attr("name");
        var curLi=$("#nav2").find("#"+thisName);
        if(upName&&upName!="undefined"){
            var preLi=$("#nav2").find("#"+upName);
            curLi.insertAfter(preLi);
        }else if(nextName&&nextName!="undefined"){
            var nextLi=$("#nav2").find("#"+nextName);
            curLi.insertBefore(nextLi);
        }else{
            $('.reportBox').sortable( 'cancel' );
        }
    });
});

//ȫ������Ч��
$(function() {
    $( ".column" ).sortable({
        connectWith: ".column",
        handle: "#moveBtn",
        placeholder: "portlet-placeholder ui-corner-all"
    });
});

//�¼���������Ч��
/*$(function() {
    $( "#trend" ).sortable({
        connectWith: "#trend",
        handle: ".round",
        placeholder: "portlet-placeholder ui-corner-all"
    });
});*/

//������Ϣ����Ч��
$(function() {
    $( "tbody" ).sortable({
        connectWith: "tbody",
        handle: ".td-move",
        placeholder: "portlet-placeholder ui-corner-all"
    });
});

$(function() {
    //�Ƴ�������Ŀ
    $(" .btn-group a.removeBtn").on("click",function() {
        var columnId=$(this).parents(".row-fluid").find(".textShow .tit").attr("id");
        if(columnId.length>3){
            columnId=columnId.substr(columnId.length - 3);
        }else{
            columnId=columnId.substr(columnId.length - 2);
        }
        $("#"+columnId).hide();
        $(this).parents(".row-fluid").remove();
    });

    //�����ı�
    $(".row-fluid .textShow .tool .btn-group .dropdown-menu").on("click",".addText",function(){
        var $li = $(textT);
        $li.find("#deleteBtn").on("click",function() {
            $(this).parents(".text").remove();
        });

        $li.find("#okBtn").on("click",function() {
            $(this).parents(".control").siblings("[contenteditable=true]").attr("contenteditable","false");
            $(this).parents(".control").find("#editorBtn").css("display","block");
            $(this).parents(".control").find("#okBtn").css("display","none");
        });

        $li.find("#editorBtn").on("click",function() {
            $(this).parents(".control").siblings("[contenteditable=false]").attr("contenteditable","true");
            $(this).parents(".control").find("#editorBtn").css("display","none");
            $(this).parents(".control").find("#okBtn").css("display","block");
        });

        $(this).parents(".row-fluid .textShow").find(".column").prepend($li);

    });

    //�༭�ı�
    $(".row-fluid .textShow .text").on("click","#editorBtn",function() {
        $(this).parents(".control").siblings("[contenteditable=false]").attr("contenteditable","true");
        $(this).parents(".control").find("#editorBtn").css("display","none");
        $(this).parents(".control").find("#okBtn").css("display","block");
    });

    //ȷ���ı�
    $(".row-fluid .textShow .text").on("click","#okBtn",function() {
        $(this).parents(".control").siblings("[contenteditable=true]").attr("contenteditable","false");
        $(this).parents(".control").find("#editorBtn").css("display","block");
        $(this).parents(".control").find("#okBtn").css("display","none");
    });

    //ɾ���ı�
    $(".row-fluid .textShow .text .control").on("click","#deleteBtn",function() {
        $(this).parents(".text").remove();
    });

    //ɾ�������
    $("table").on("click",".td-delete",function() {
        $(this).parents("tr").remove();
    });





    //��ӿɱ༭�����
    $(" #hotTable").on("click",".td-add",function() {
        var $li = $(hotTableT);
        $li.find("table").on("click",".td-delete",function() {
            $(this).parents("tr").remove();
        });
        $(hotTableT).clone(true).prependTo("#hotTable");
    });

    //�ȵ�����ѡ����ʽ
    $(".yqtabs .tool .btn-group").on("click",".listMo",function() {
        $(this).parents(".yqtabs").addClass("circularMWBList");
        $(this).css("display","none");
        $(this).siblings(".cirMo").css("display","block");
    });

    $(".yqtabs .tool .btn-group").on("click",".cirMo",function() {
        $(this).parents(".yqtabs").removeClass("circularMWBList");
        $(this).css("display","none");
        $(this).siblings(".listMo").css("display","block");
    });

//����ͳ��ͼ��
    $(".row-fluid .textShow .tool .btn-group .dropdown-menu").on("click",".addChart",function(){
        var $this = $(this);
        var $rowMapbox = $this.parents(".row-fluid").find(".textShow .column");
        $(this).parents(".row-fluid .textShow .tool .btn-group .dropdown-menu").find(".addChart").siblings(".chartMapBK").clone(true).prependTo($rowMapbox);
    });


    //ͳ��ͼ��ʽѡ��Ч��
    $(".chartChoice >ul>li").on("click",function(){
        var $this = $(this);
        var $rowMapimg = $this.find("p img").attr("src");
        $this.siblings("li").removeClass("clicked");
        $(this).addClass("clicked");
        $(this).parents(".row-fluid .textShow .column .chartMapBK").find(".picBox img").attr("src",$rowMapimg);
    });

    //ȷ��ͳ��ͼ��
    /*$(".row-fluid .textShow .chartMapBK  .control").on("click","#okBtn",function(){
        $(this).parents(".row-fluid .textShow .chartMapBK .chartMap").find(".chartChoice").css("display","none");
        $(this).parents(".row-fluid .textShow .chartMapBK .chartMap").find(".picBox").css("display","block");
        $(this).parents(".row-fluid .textShow .chartMapBK .chartMap").find(".control #editorBtn").css("display","block");
        $(this).parents(".row-fluid .textShow .chartMapBK .chartMap").find(".control #okBtn").css("display","none");
    });*/

    //ȷ��ͳ��ͼ��
    $(".row-fluid .chartMapBK  .control").on("click","#okBtn",function(){
        $(this).parents(".row-fluid .chartMapBK").find(".chartChoice").css("display","none");
        $(this).parents(".row-fluid .chartMapBK").find(".picBox").css("display","block");
        $(this).parents(".row-fluid .chartMapBK").find(".control #editorBtn").css("display","block");
        $(this).parents(".row-fluid .chartMapBK").find(".control #okBtn").css("display","none");
        $(this).parents(".row-fluid  .chartMapBK ").find(".parameterBox").css("display","block");
    });

    //�༭ͳ��ͼ��
  /*  $(".row-fluid .textShow .chartMapBK .control").on("click","#editorBtn",function(){
        $(this).parents(".row-fluid .textShow .chartMapBK .chartMap").find(".chartChoice").css("display","block");
        $(this).parents(".row-fluid .textShow .chartMapBK .chartMap").find(".picBox").css("display","none");

        $(this).parents(".row-fluid .textShow .chartMapBK .chartMap").find(".control #editorBtn").css("display","none");
        $(this).parents(".row-fluid .textShow .chartMapBK .chartMap").find(".control #okBtn").css("display","block");
    });*/

    //�༭ͳ��ͼ��
    $(".row-fluid .chartMapBK .control").on("click","#editorBtn",function(){
        $(this).parents(".row-fluid  .chartMapBK ").find(".chartChoice").css("display","block");
        $(this).parents(".row-fluid  .chartMapBK ").find(".picBox").css("display","none");
        $(this).parents(".row-fluid  .chartMapBK ").find(".parameterBox").css("display","none");
        $(this).parents(".row-fluid  .chartMapBK ").find(".control #editorBtn").css("display","none");
        $(this).parents(".row-fluid  .chartMapBK ").find(".control #okBtn").css("display","block");

    });

//ɾ��ͳ��ͼ��
    /*$(" .row-fluid .textShow .chartMapBK .control").on("click","#deleteBtn",function() {
        $(this).parents(".chartMapBK").remove();
    });*/
    $(" .row-fluid .chartMapBK .control").on("click","#deleteBtn",function() {
        $(this).parents(".chartMapBK").remove();
    });


//�༭�۵�
    var num=0;
    $('.chartMap .control #editorGDBtn').click(function(e){
        if(num++ %2 == 0){
            $(this).parents(".row-fluid .textShow").find(".viewpoint [contenteditable=false]").attr("contenteditable","true");
        }else{
            $(this).parents(".row-fluid .textShow").find(".viewpoint [contenteditable=true]").attr("contenteditable","false");
        }
        e.preventDefault(); //��ֹԪ�ص�Ĭ�϶�����������ڣ�
    });




    $('#hotTable tr').find('td:last-child').hide();
    $('#hotTable tr').find('th:last-child').hide();
    //�л����༭״̬
    $(" .btn-group a.editBtn").on("click",function() {
        $("[contenteditable=true]").attr("contenteditable","false");
        $(".editBtn").css("display","block");
        $(".saveBtn").css("display","none");

        $(this).parents(".row-fluid .textShow .btn-group").find(".editBtn").css("display","none");
        $(this).parents(".row-fluid .textShow .btn-group").find(".saveBtn").css("display","block");
        $(".yb-move").css("display","none");
        $(".yb-delete").css("display","none");
        $(".yb-add").css("display","none");
        $(this).parents(".row-fluid .textShow").find(".yb-move").css("display","block");
        $(this).parents(".row-fluid .textShow").find(".yb-delete").css("display","block");
        $(this).parents(".row-fluid .textShow").find(".yb-add").css("display","block");

        $('#hotTable tr').find('td:last-child').hide();
        $('#hotTable tr').find('th:last-child').hide();

        $(this).parents('.row-fluid .textShow').find('#hotTable tr td:last-child').show();
        $(this).parents('.row-fluid .textShow').find('#hotTable tr th:last-child').show();

        $(this).parents(".row-fluid .textShow").find("[contenteditable=false]").attr("contenteditable","true");
        $(this).parents(".row-fluid .textShow").siblings(".textShow").find("[contenteditable=false]").attr("contenteditable","true");

    });

    //�л���Ԥ��״̬
    $(" .btn-group a.saveBtn").on("click",function() {
        $(this).parents(".row-fluid .textShow .btn-group").find(".editBtn").css("display","block");
        $(this).parents(".row-fluid .textShow .btn-group").find(".saveBtn").css("display","none");
        $(this).parents(".row-fluid .textShow").find(".yb-move").css("display","none");
        $(this).parents(".row-fluid .textShow").find(".yb-delete").css("display","none");
        $(this).parents(".row-fluid .textShow").find(".yb-add").css("display","none");

        $(this).parents('.row-fluid .textShow').find('#hotTable tr td:last-child').hide();
        $(this).parents('.row-fluid .textShow').find('#hotTable tr th:last-child').hide();

        $(this).parents(".row-fluid .textShow").find("[contenteditable=true]").attr("contenteditable","false");
        $(this).parents(".row-fluid .textShow").siblings(".textShow").find("[contenteditable=true]").attr("contenteditable","false");
    });
});

function add(){
    //���뵥���¼�����
    var $li = $(trendT);
    $li.find("#trend").on("click",".yb-delete",function() {
        $(this).parents("li").remove();
    });
    $(trendT).clone(true).prependTo("#trend");

}

function del(obj){
    //ɾ�������¼�����
    $(obj).parents("#trend li").remove();

}

function delHot(obj){
    $(obj).parents(".mwblist li").hide();
}



 
