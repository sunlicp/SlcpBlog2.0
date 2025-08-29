<template>
    <div class="wall-index">
        <TopBar></TopBar>
        <router-view name="mobile"></router-view>
        <video class="bg-video" src="@/images/qm1.mp4" loop="loop" autoplay="autoplay" muted="muted" ></video>
    </div>
</template>
<script setup>
import TopBar from './TopBar.vue';
import { useStore } from 'vuex';
import { onMounted } from 'vue'
import axios from 'axios';
const store = useStore()
const getUser = () => {
    // signIpApi().then(res=>{
    //     let user = {
    //         id : res.data.query
    //     }
    // store.commit('getUser',user)
    // })
    axios.get("https://api.ipify.org").then(res => {
        let user = {
            id : res.data
            }
        store.commit('getUser',user)
    }).catch(res => {
        let user= {
            id : '127.0.0.1'
            };
        store.commit('getUser',user)
    })
}
onMounted(()=>{
    getUser()
})
</script>
<style lang="less" scoped>
.wall-index{
    .bg-video{
        position: fixed;
        top: 0;
        left: 0;
        z-index: -1;
        height: 880px;
    }
}
</style>