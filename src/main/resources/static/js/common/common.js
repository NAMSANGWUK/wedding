/**
 * @function getContextPath : ContextPath 가져오기
 */
const getContextPath = () => {
  const pathName = window.location.pathname;
  return pathName.substring(0, pathName.indexOf('/', 1));
}

/**
 * @function linking : 경로지정 이벤트
 */
const linking = (path) => {
  location.href = getContextPath() + path;
}