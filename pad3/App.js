/**
 * Sample React Native App
 *
 * adapted from App.js generated by the following command:
 *
 * react-native init example
 *
 * https://github.com/facebook/react-native
 */

import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View,TouchableHighlight,requireNativeComponent,NativeEventEmitter } from 'react-native';
import Palmmob3AdLibs from 'react-native-palmmob3-ad-libs';

const SplashView = requireNativeComponent("GDTSplashView");
const PangleSplashView = requireNativeComponent("PangleSplashView");
const eventEmitter = new NativeEventEmitter(Palmmob3AdLibs)
const REWARD_EVT_PREFIX = 'RewardAd_';

export default class App extends Component {
  state = {
    status: 'starting',
    message: '--',
    inited:false
  };

  subscribeRewardEvent(type){
    eventEmitter.addListener(REWARD_EVT_PREFIX + type, (event) => {
      console.log(event);      
    })
  }

  async componentDidMount() {
    await Palmmob3AdLibs.initGDT('1111964523');
    await Palmmob3AdLibs.setGDTChannel(10);

    await Palmmob3AdLibs.initPangle('5207398', "face", true);    

    this.setState({
      inited:true
    });

    this.subscribeRewardEvent('onAdError');
    this.subscribeRewardEvent('onAdClick');
    this.subscribeRewardEvent('onAdClose');
    this.subscribeRewardEvent('onAdLoad');
    this.subscribeRewardEvent('onAdShow');
    this.subscribeRewardEvent('onAdComplete');
    this.subscribeRewardEvent('onAdReward');    
  }

  onAdError(evt) {
    console.log(evt.nativeEvent);
  }

  onAdClick(evt) {
    console.log(evt.nativeEvent);
  }

  onAdClose(evt) {
    console.log(evt.nativeEvent);
    this.setState({
      inited:false
    });
  }

  onAdLoad(evt) {
    console.log(evt.nativeEvent);
  }

  onAdShow(evt) {
    console.log(evt.nativeEvent);
  }

  showReward(){
    Palmmob3AdLibs.loadRewardVideo('8052501657212786');
  }

  render() {
    if(this.state.inited){
      return ( 
        <View style={styles.container}>
          {/* <SplashView style={styles.splash} PosId='3032809667717528' 
              onAdError={this.onAdError.bind(this)}  onAdClick={this.onAdClick.bind(this)} 
              onAdClose={this.onAdClose.bind(this)}  onAdLoad={this.onAdLoad.bind(this)} 
              onAdShow={this.onAdShow.bind(this)}  /> */}
          <PangleSplashView style={styles.splash} PosId='887541836' 
              onAdError={this.onAdError.bind(this)}  onAdClick={this.onAdClick.bind(this)} 
              onAdClose={this.onAdClose.bind(this)}  onAdLoad={this.onAdLoad.bind(this)} 
              onAdShow={this.onAdShow.bind(this)}  />              
        </View>
      );
    }
    return (
      <View style={styles.container}>
        <TouchableHighlight onPress={this.showReward.bind(this)} ><Text style={styles.welcome}>☆Palmmob3AdLibs example☆</Text></TouchableHighlight>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#00FF00',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  splash: {    
    // flex:1,
    width:"100%",
    height:"100%"
  }
});
