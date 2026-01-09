document.addEventListener('DOMContentLoaded', () => {

    /* ===============================
       FAB : 모바일/태블릿 사이드바 열기/닫기
       =============================== */
    const fab = document.getElementById('sidebarFab');
    if (fab) {
        fab.addEventListener('click', () => {
            document.body.classList.toggle('sidebar-open');
        });
    }

    /* ===============================
       관리자 compact → 아이콘 클릭 시
       사이드바 자체를 확장
       =============================== */
    const adminMenuButtons =
        document.querySelectorAll('#sidebar-admin .menu-main');

    adminMenuButtons.forEach(btn => {
        btn.addEventListener('click', (e) => {

            // compact 상태가 아니면 기존 동작 유지
            if (!document.documentElement.classList.contains('sidebar-compact')) {
                return;
            }

            // 관리자 sidebar에서만
            if (!btn.closest('#sidebar-admin')) return;

            // 기본 클릭 동작 중단
            e.preventDefault();
            e.stopPropagation();

            // ▶ 사이드바 확장
            document.documentElement.classList.remove('sidebar-compact');
            try {
                sessionStorage.setItem('sidebarCompact', 'false');
            } catch (e) {}

            // (선택) 확장 후 해당 메뉴 자동 오픈 원하면 사용
            // const menuGroup = btn.closest('.menu-group');
            // if (menuGroup) menuGroup.classList.add('menu-open');
        });
    });

});

