import Timeline from './src/main.vue'

/* istanbul ignore next */
Timeline.install = function(Vue) {
  Vue.component(Timeline.name, Timeline)
}

export default Timeline
