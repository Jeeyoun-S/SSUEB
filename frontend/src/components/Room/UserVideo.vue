<template>
<div v-if="streamManager">
	<div id="main-video-stream-container">
		<OvVideo :stream-manager="streamManager"/>
		<v-chip class="mt-2" color="primary">
			{{ clientData }}
		</v-chip>
		<!-- <div>{{ clientData }}</div> -->
	</div>
</div>
</template>

<script>
import OvVideo from './OvVideo';

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
	},

	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
};
</script>
<style>
#main-video-stream-container{
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
</style>