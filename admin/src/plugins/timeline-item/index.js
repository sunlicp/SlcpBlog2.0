import ElTimelineItem from '../timeline/src/item.vue'

/* istanbul ignore next */
ElTimelineItem.install = function(Vue) {
  Vue.component(ElTimelineItem.name, ElTimelineItem)
}

export default ElTimelineItem
