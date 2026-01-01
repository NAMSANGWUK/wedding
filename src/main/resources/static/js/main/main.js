

function gotoLoginPage(){
  linking("/login");
}

$(document).ready(function(){
  $('#gotoLoginPageBtn').on('click', gotoLoginPage);
})