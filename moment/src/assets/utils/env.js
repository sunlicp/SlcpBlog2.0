let baseUrl = ''
let routerMode = 'hash'
let avatarUrl = ''
//let baseImgPath
if(process.env.NODE_ENV == 'development'){
    baseUrl = 'http://127.0.0.1:81/'
    //baseImgPath = 'https://img.slcp.top'
    avatarUrl = 'https://img.slcp.top/me.jpg'
}else{
    baseUrl = 'https://slcp.top/'
    //baseImgPath = 'https://api.5i21.cn'
    avatarUrl = 'https://img.slcp.top/me.jpg'
}
export {
    baseUrl,
    routerMode,
    //baseImgPath,
    avatarUrl
}