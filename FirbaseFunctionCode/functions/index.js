const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();
const db = admin.firestore();

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions


exports.createUserData = functions.auth.user().onCreate((user) => {
	const data = {
		name: user.displayName,
		email: user.email,
		phoneNumber: user.phoneNumber,
		friends: []
	};
	return db.collection('users').doc(user.uid).set(data);
});

exports.deleteUser = functions.auth.user().onDelete((user) => {
	return db.collection('users').doc(user.uid).delete();
});

// Adds extra user data, should be used when creating user with email and pass
exports.addUserData = functions.https.onCall((data, context) => {
	const userData = {
		name: data.name,
		email: context.auth.token.email,
		phoneNumber: data.phoneNumber,
		friends: []
	};
	return db.collection('users').doc(context.auth.uid).set(userData);
});