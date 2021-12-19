<template align="left">
  <div align="left">
    <v-card class="mx-auto" max-width="400" tile>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title>회원님의 보유 결제수단</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title>할인 쿠폰</v-list-item-title>
          <div
            v-if="
              userInfo[0].couponA != 0 ||
              userInfo[0].couponB != 0 ||
              userInfo[0].couponC != 0
            "
          >
            <div v-for="(item, index) in this.couponData" :key="index">
              <li v-if="item.cnt > 0">{{ item.type }}% - {{ item.cnt }}개</li>
            </div>
          </div>

          <div v-else>사용 가능한 할인 쿠폰이 없습니다.</div>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title>포인트</v-list-item-title>
          <div v-if="userInfo[0].point != 0">
            <li v-for="(item, index) in this.userdata.pointList" :key="index">
              {{ item.balancePoint }}원 (~{{ item.expirationDate }})까지 사용
              가능
            </li>
          </div>
          <div v-else>사용 가능한 포인트가 없습니다.</div>
        </v-list-item-content>
      </v-list-item>

      <v-list-item two-line>
        <v-list-item-content>
          <v-list-item-title>적립금</v-list-item-title>
          <li>{{ this.savemoneyData.haveSavemoney }}원</li>
        </v-list-item-content>
      </v-list-item>
    </v-card>
  </div>
</template>

<script>
import { getUser } from "@/api/payment";
import { mapActions, mapState } from "vuex";
const userInfoStore = "userInfoStore";
export default {
  name: "UserData",
  data() {
    return {
      userdata: [],
      couponData: [
        { type: 20, cnt: 0 },
        { type: 10, cnt: 0 },
        { type: 5, cnt: 0 },
      ],
      savemoneyData: { haveSavemoney: 0, useSavemoney: 0 },
      pointData: { havePoint: 0, usePoint: 0 },
      info: [],
      userNo: 2,
    };
  },
  computed: {
    ...mapState(userInfoStore, ["userInfo"]),
  },
  created() {
    getUser(
      this.userNo,
      (response) => {
        this.userdata = response.data;
        // 적립금
        this.savemoneyData.haveSavemoney =
          this.userdata.savemoneyList.saveMoney;
        // 포인트
        for (var j = 0; j < this.userdata.pointList.length; j++) {
          this.pointData.havePoint += this.userdata.pointList[j].balancePoint;
        }
        // 쿠폰
        for (var i = 0; i < this.userdata.couponList.length; i++) {
          if (this.userdata.couponList[i].type == 20) {
            this.couponData[0].cnt++;
          } else if (this.userdata.couponList[i].type == 10) {
            this.couponData[1].cnt++;
          } else if (this.userdata.couponList[i].type == 5) {
            this.couponData[2].cnt++;
          }
        }
        this.storeUserInfo();
      },
      (error) => {
        console.log(error);
      }
    );
  },
  methods: {
    ...mapActions(userInfoStore, ["regUserInfo"]),
    storeUserInfo() {
      this.info.push({
        couponA: this.couponData[0].cnt,
        couponB: this.couponData[1].cnt,
        couponC: this.couponData[2].cnt,
        saveMoney: this.userdata.savemoneyList.saveMoney,
        point: this.pointData.havePoint,
      });
      this.regUserInfo(this.info);
    },
  },
};
</script>

<style></style>
