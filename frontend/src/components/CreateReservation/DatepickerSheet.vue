<template>
    <Datepicker v-model="date"></Datepicker>
</template>

<script>
  import Datepicker from '@vuepic/vue-datepicker';
  import '@vuepic/vue-datepicker/dist/main.css'

  export default 
  {
    name: 'DatepickerSheet',
    components: {Datepicker},
      data() {return {date: null,};}
  }

</script>

<!--
    Alternatively, you can import the scss file if you want full control of the component styles
    @import '@vuepic/vue-datepicker/src/VueDatePicker/style/main.scss';
-->