const userValidStore = {
  state: {
    userRule: {
      email: [
        (v) => !!v || "이메일은 필수 입력 사항입니다.",
        (v) =>
          /^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9-.]{2,4}$/.test(v) ||
          "이메일 형식으로 입력해 주세요.",
        (v) => v.length <= 30 || "30자 이하로 입력해 주세요.",
      ],
      password: [
        (v) => !!v || "비밀번호는 필수 입력 사항입니다.",
        (v) =>
          /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[~!@$!%*#^?&])(?!.*[^a-zA-z0-9~!@$!%*#^?&]).{10,20}$/.test(
            v
          ) || "영어, 숫자, 특수문자 포함 10~20자로 입력해 주세요.",
      ],
      name: [
        (v) => !!v || "이름은 필수 입력 사항입니다.",
        (v) => v.length <= 22 || "22자 이하로 입력해 주세요.",
        (v) =>
          /^[가-힣|a-z|A-Z]{1,22}$/.test(v) ||
          "숫자, 특수문자는 입력 불가능합니다.",
      ],
      phone: [
        (v) => !!v || "휴대폰 번호는 필수 입력 사항입니다.",
        (v) => /^[0-9]{11}$/.test(v) || "'-' 없이 숫자 11자리로 입력해 주세요.",
      ],
      nickname: [
        (v) => !!v || "닉네임은 필수 입력 사항입니다.",
        (v) => v.length <= 22 || "22자 이하로 입력해 주세요.",
        (v) =>
          /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-z|A-Z|0-9]{1,22}$/.test(v) ||
          "특수문자는 입력 불가능합니다.",
      ],
    },
    consultantRule: {
      license: [
        (v) => !!v || "필수 입력 사항입니다.",
        (v) =>
          /^[0-9a-zA-z-]*$/.test(v) || "숫자, 영문자, -만 입력 가능합니다.",
        (v) => v.length <= 30 || "30자 이하로 입력해 주세요.",
      ],
      image: [
        (v) => v.length == 1 || "자격증 사본을 하나의 파일로 첨부해 주세요.",
        (v) =>
          v[0].size <= 5000000 || "첨부 파일 크기는 최대 5MB까지만 가능합니다.",
        (v) =>
          ["image/png", "image/jpeg", "application/pdf"].includes(v[0].type) ||
          "png, jpg, pdf 파일만 첨부 가능합니다.",
      ],
      type: [
        (v) => v.length >= 1 || "상담 가능한 동물을 하나 이상 선택해 주세요.",
      ],
      profile: [
        (v) => v == null || v.length <= 1 || "이미지는 하나만 첨부 가능합니다.",
        (v) =>
          v == null ||
          v.length == 0 ||
          (v.length == 1 && ["image/png", "image/jpeg"].includes(v[0].type)) ||
          "png, jpg 파일만 첨부 가능합니다.",
      ],
      intro: [(v) => v.length <= 150 || "150자 이하로 입력해 주세요."],
    },
    petRule: {
      petBirth: [
        (v) =>
          !v ||
          /^\d{4}-(0[1-9]|1[012])$/.test(v) ||
          "생일은 YYYY-MM 형식으로 입력해 주세요.",
      ],
      petImage: [
        (v) =>
          v == null || v.length <= 1 || "프로필 사진은 하나만 첨부 가능합니다.",
        (v) =>
          v == null ||
          v.length == 0 ||
          (v.length == 1 && ["image/png", "image/jpeg"].includes(v[0].type)) ||
          "png, jpg 파일만 첨부 가능합니다.",
      ],
      petInfo: [
        (v) => v == null || v.length <= 40 || "20자 이하로 입력해 주세요.",
      ],
      petName: [
        (v) => !!v || "이름은 필수 입력 사항입니다.",
        (v) =>
          !/[{}[\]/?.,;:|)|*~`!^\-_+<>@#$%&\\=('"]/.test(v) ||
          "특수문자 입력은 불가능합니다.",
        (v) => v.length <= 20 || "20자 이하로 입력해 주세요.",
      ],
      petType: [(v) => !!v || "종류 선택은 필수입니다."],
      petVariety: [
        (v) =>
          v == null ||
          !v ||
          !/[{}[\]/?.,;:|)|*~`!^\-_+<>@#$%&\\=('"]/.test(v) ||
          "특수문자 입력은 불가능합니다.",
        (v) => v == null || v.length <= 20 || "20자 이하로 입력해 주세요.",
      ],
    },
  },
  getters: {
    getUserRule(state) {
      return state.userRule;
    },
    getConsultantRule(state) {
      return state.consultantRule;
    },
    getPetRule(state) {
      return state.petRule;
    },
  },
  mutations: {},
  actions: {},
};

export default userValidStore;
