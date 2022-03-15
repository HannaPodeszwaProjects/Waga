package pl.polsl.utils;

public enum WindowSize {//należy dodać do okienka z scenbuildera w: +15, h: +40
    //ADMINISTRATOR
    adminMenuForm (215,571),
    manageAdminForm (516, 430),
    addOrUpdateAdminForm(317,291),
    //class
    manageClassForm (321,340),
    addOrUpdateClassForm(305,251),
    //schedule
    manageScheduleForm(971,630),
    manageLessonHoursForm(416,347),
    //subject
    manageSubjectsForm(461,356),
    addOrUpdateSubjectForm(255,186),
    //student
    manageStudentsForm(501, 396),
    addOrUpdateStudentForm(270, 321),
    //teacher
    manageTeachersForm (516, 430),
    addOrUpdateTeacherForm(317,291),
    teacherMenuForm(205,411),
    teacherAbsenceForm(545,410),
    teacherAddNewAbsenceForm(385,291),
    teacherAddNewClubForm(245,251),
    teacherAddNewCompetitionForm(245, 320),
    teacherAddNewGradeForm(375,351),
    teacherAddNewNoteForm(375,265),
    TeacherAssignStudentToClubForm(461, 306),
    TeacherAssignStudentToCompetitionForm(461, 306),
    teacherClubForm(461,406),
    teacherCompetitionForm(645,387),
    teacherGradeForm(501,465),
    teacherNoteForm(461,376),
    teacherScheduleForm(926,440),
    //parent
    manageParentsForm (571, 502),
    addOrUpdateParentForm(371,543),
    parentMenuForm(205,331),
    //classroom
    manageClassroomsForm(441,335),
    addOrUpdateClassroomForm(345,251),
    //student
    studentMenuForm(205,411),
    studentAbsenceForm(471,402),
    studentGradesForm(545,402),
    studentScheduleForm(971,606),
    studentClubsForm(391, 373),
    studentCompetitionsForm(591, 375),
    studentNoteForm(425, 415),
    //common
    changePasswordForm(210,320),
    messageForm(855,440),
    messagerForm(855,440),
    resetPasswordForm(205,385),
    viewMessageForm(855,440),
    signIn(205,290),
    //principal
    principalMenuForm(215,571),
    raportMenuForm(505,346),
    newPrincipalForm(375,179),
    ;


    private static final int defaultSize = 600;
    private final int width;
    private final int height;

    WindowSize(final int w,final int h) {
        width=w;
        height=h;
    }
    public int getWidth() {
        if(width!=0)
        return width;
        return defaultSize;
    }

    public int getHeight() {
        if(height!=0)
        return height;
        return defaultSize;
    }
}
