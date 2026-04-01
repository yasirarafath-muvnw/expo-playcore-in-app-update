import { requireOptionalNativeModule } from 'expo-modules-core';
import { Platform } from 'react-native';

// This links to the "Name" we define in the Kotlin file
// We use requireOptionalNativeModule so it doesn't crash on iOS since we deleted the iOS module
const ExpoInAppUpdate = requireOptionalNativeModule('ExpoInAppUpdate');

export function triggerImmediateUpdate() {
    if (Platform.OS !== 'android' || !ExpoInAppUpdate) {
        console.warn("In-App Updates are only supported on Android.");
        return;
    }
    return ExpoInAppUpdate.triggerImmediateUpdate();
}