import React, { useEffect, useState } from 'react';
import {
  // Alert,
  Button,
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  TextInput,
  View,
  Alert,
} from 'react-native';

// in js
import { NativeModules } from 'react-native';
const Threads = NativeModules.Threads;

type SectionProps = {
  title: string;
  children: React.ReactNode;
};

interface ThreadsTempCode {
  code: string;
}

function Section({ children, title }: SectionProps): React.JSX.Element {
  return (
    <View style={styles.sectionContainer}>
      <Text style={styles.sectionTitle}>{title}</Text>
      <Text style={styles.sectionDescription}>{children}</Text>
    </View>
  );
}

function App(): React.JSX.Element {
  const [userInput, setUserInput] = useState('');

  function getUrlParams(urlString) {
    var params = {};

    urlString.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (str, key, value) {
      params[key] = value;
    });

    return params;
  }

  // useEffect(() => {
  //   Linking.addEventListener('url', async (e) => {
  //     const searchParams = getUrlParams(e.url);
  //     // const searchParams = new URLSearchParams(e.url);
  //     // console.log(searchParams.getAll("code"));
  //     // console.log(searchParams);
  //     console.log("======== raw value ======")
  //     console.log(e.url);
  //     console.log("========")

  //     const code = e.url.split('code=')[1];

  //     // console.log(searchParams["code"]);

  //     console.log("======== code =======")

  //     console.log(code);

  //     // const tempThreadsCode = searchParams["code"];
  //     const resultFromAndroid = await NativeModules.ThreadsModule.shareThreads('userInput', code);

  //     console.log(resultFromAndroid);
  //   });
  // }, []);

  return (
    <SafeAreaView style={styles.backgroundStyle}>
      <StatusBar
        barStyle="dark-content"
        backgroundColor={styles.backgroundStyle.backgroundColor}
      />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={styles.backgroundStyle}>
        <View style={styles.container}>
          <Section title="Android / iOS Module - Cli">
            <TextInput
              style={styles.textInput}
              value={userInput}
              onChangeText={setUserInput}
              placeholder="Enter text here"
              placeholderTextColor="#888888"
            />
          </Section>
          <Text>userInput: {userInput}</Text>
          <Button
            onPress={async () => {
              const code =
                await NativeModules.ThreadsModule.openShareThreadsWeb(
                  userInput,
                );
              Alert.alert(code);
            }}
            title="Share Threads"
            color="#f194ff"
          />

          <Button
            onPress={() => {
              Threads.shareThreads(userInput);
              // Threads.callFromJs(userInput, 'secondValue', (firstValue, secondValue) => {
              //   console.log(firstValue);
              //   console.log(secondValue);
              // });
            }}
            title="Share Threads with iOS"
            color="#f194ff"
          />
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  backgroundStyle: {
    backgroundColor: '#f0f0f0', // 밝은 배경색
    flex: 1,
  },
  container: {
    backgroundColor: '#ffffff', // 컨테이너 배경색
    padding: 20,
  },
  textInput: {
    backgroundColor: '#ff22ff',
    height: 40,
    padding: 10,
    borderRadius: 5,
    marginVertical: 10,
    fontSize: 16,
    color: '#000000', // 텍스트 색상 (검은색)
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: '#000000', // 제목 색상 (검은색)
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: '#333333', // 설명 텍스트 색상 (어두운 회색)
  },
});

export default App;
