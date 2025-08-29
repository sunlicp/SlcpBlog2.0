import fetch from '../utils/axios'
//新建数据
export function insertWallApi(data){
    return fetch.post('/momentTime/insertwall',data)
}
//获取IP
export function signIpApi(){
    return fetch.post('/momentTime/signip')
}
//查询墙
export function findWallPageApi(data){
    return fetch.get('/momentTime/findwallpage?type='+ data.type + '&label=' + data.label + '&userId=' + data.userId + '&pageNum=' + data.page + '&pageSize=' + data.pagesize)
}
//新建反馈
export function insertFeedBackApi(data){
    return fetch.post('/momentTime/insertfeedback',data)
}
//新建评论
export function insertCommentApi(data){
    return fetch.post('/momentTime/insertcomment',data)
}
//获取评论
export function findCommentPage(data){
    return fetch.get('/momentTime/findcommentpage?' + 'id=' + data.id + '&pageNum=' + data.page + '&pageSize=' + data.pagesize)
}
//上传图片
export function proFileApi(data){
    return fetch.post('/momentTime/profile',data)
}