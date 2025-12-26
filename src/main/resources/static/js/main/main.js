

function gotoLoginPage(){
  window.location.href="/login"
}

document.addEventListener("DOMContentLoaded",function(){
  document.querySelector("#gotoLoginPageBtn").addEventListener("click", gotoLoginPage)
});