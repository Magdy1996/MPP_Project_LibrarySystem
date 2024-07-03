package business;

import java.util.*;
import java.time.LocalDate;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class Main {

	public static void main(String[] args) {
		System.out.println(allWhoseZipContains3());
		System.out.println(allHavingOverdueBook());
		System.out.println(allHavingMultipleAuthors());
	}

	// Returns a list of all ids of LibraryMembers whose zipcode contains the digit 3
	public static List<String> allWhoseZipContains3() {
		DataAccess da = new DataAccessFacade();
		Collection<LibraryMember> members = da.readMemberMap().values();
		List<String> filteredMemberIds = new ArrayList<>();
		for (LibraryMember member : members) {
			if (member.getAddress().getZip().contains("3")) {
				filteredMemberIds.add(member.getMemberId());
			}
		}
		return filteredMemberIds;
	}

	// Returns a list of all ids of LibraryMembers that have an overdue book
	public static List<String> allHavingOverdueBook() {
		DataAccess da = new DataAccessFacade();
		Collection<LibraryMember> members = da.readMemberMap().values();
		List<String> overdueMemberIds = new ArrayList<>();
		for (LibraryMember member : members) {
			if (member.getCheckoutRecord().getEntries().stream().anyMatch(entry ->
					entry.getDueDate().isBefore(LocalDate.now()) && !entry.isReturned())) {
				overdueMemberIds.add(member.getMemberId());
			}
		}
		return overdueMemberIds;
	}

	// Returns a list of all ISBNs of Books that have multiple authors
	public static List<String> allHavingMultipleAuthors() {
		DataAccess da = new DataAccessFacade();
		Collection<Book> books = da.readBooksMap().values();
		List<String> booksWithMultipleAuthors = new ArrayList<>();
		for (Book book : books) {
			if (book.getAuthors().size() > 1) {
				booksWithMultipleAuthors.add(book.getIsbn());
			}
		}
		return booksWithMultipleAuthors;
	}
}
