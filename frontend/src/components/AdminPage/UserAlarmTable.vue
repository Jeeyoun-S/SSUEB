<template>
  <v-sheet class="mr-2" width="150">
    <v-combobox
      :items="['이메일', '이름', '닉네임']"
      variant="outlined" density="compact" v-model="range"
      hide-details
    ></v-combobox>
  </v-sheet>
  <v-text-field
    class="mb-1"
    v-model="keyword"
    density="compact"
    variant="outlined"
    label="검색"
    append-inner-icon="mdi-magnify"
    single-line
    hide-details
  ></v-text-field>
  <v-sheet class="pt-3" width="100%">
    <v-textarea label="알람 내용 작성" variant="outlined"
      counter="40" maxlength="40" v-model="content"
    ></v-textarea>
  </v-sheet>
  <v-table fixed-header hover>
    <thead>
      <tr>
        <th width="96">번호</th>
        <th width="320">이메일</th>
        <th width="180">이름</th>
        <th width="180">닉네임</th>
        <th width="150">권한</th>
        <th width="100"></th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(user, index) in userList" :key="index"
        style="cursor: pointer;"
      >
        <td>{{ String(index).padStart(4, '0') }}</td>
        <td>{{ user.id }}</td>
        <td>{{ user.userName }}</td>
        <td>{{ user.userNickname }}</td>
        <td>{{ user.authorities[0].authorityName.substr(5, 10) }}</td>
        <td>
          <v-btn class="mr-3" variant="outlined" color="primary" rounded="0"
            @click="sendAlarm(user.id)"
          >
            전송
          </v-btn>
        </td>
      </tr>
    </tbody>
  </v-table>
</template>

<script>
export default {
  name: "UserAlarmTable",
  props: {
    userList: Array
  },
  data() {
    return {
      range: "이메일",
      dialog: false,
      content: null
    }
  },
  methods: {
    sendAlarm(id) {
      alert(id);
    }
  }
}
</script>

<style>

</style>